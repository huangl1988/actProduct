package com.newtouch.act.service.service.impl;

import cn.newtouch.config.LocalProducer;
import cn.newtouch.exception.BusinessException;
import cn.newtouch.feign.ITransCore;
import com.lmax.disruptor.RingBuffer;
import com.newtouch.act.service.common.Constants;
import com.newtouch.act.service.entity.ActivityExchangePojo;
import com.newtouch.act.service.entity.ActivityPojo;
import com.newtouch.act.service.entity.ActivityTagPojo;
import com.newtouch.act.service.entity.ActivityTaskPojo;
import com.newtouch.act.service.mapper.IActivityExchange;
import com.newtouch.act.service.mapper.IActivityMapper;
import com.newtouch.act.service.mapper.IActivityTags;
import com.newtouch.act.service.mapper.IActivityTask;
import com.newtouch.act.service.service.ActivityService;
import com.newtouch.benifit.client.feign.IBenifit;
import com.newtouch.entity.*;
import com.newtouch.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by steven on 2018/4/16.
 */
@Service
public class ActivityServiceImpl implements ActivityService {



    @Autowired
    @Qualifier("activityMapper")
    IActivityMapper activityMapper;

    @Autowired
    IActivityTags activityTags;

    @Autowired
    IActivityExchange activityExchange;

    @Autowired
    IActivityTask activityTask;

    @Autowired
    JedisSentinelPool jedisSentinelPool;

    @Autowired
    IBenifit benifitClient;

    @Autowired
    ITransCore transCore;

    @Override
    public List<Activity> getActivities(String time, Long actId) {
        List<Activity> activities = new ArrayList<>();
        List<ActivityPojo> list=activityMapper.getActivityPojos(time,actId);
        Optional.ofNullable(list).ifPresent(activityPojos -> {
            activityPojos.stream().forEach(activityPojo -> {
                activities.add(toActivity(activityPojo));
            });
        });
        return activities;
    }

    @Override
    public BaseResponse rush(Long id, String commodyCode, String orderNo) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setOrderNo(orderNo);

        try(Jedis jedis = jedisSentinelPool.getResource()){
            if(jedis.decr(Constants.REDIS_KEY.ACT_INFO_PRE_.name()+id)>-1){
                if(jedis.decr(Constants.REDIS_KEY.ACT_INFO_PRE_.name()+id+"_"+commodyCode)>-1){
                    baseResponse.setCode("succ");
                    Map<String,String> map = new HashMap<>();
                    map.put("id",Long.toString(id));
                    map.put("commodyCode",commodyCode);
                    map.put("orderNo",orderNo);
                    map.put("topic","act-insert");
                    LocalProducer.send(map);
                    return baseResponse;
                }
                baseResponse.setCode("fail commody");
                return baseResponse;
            }
            baseResponse.setCode("fail");
        }

        return baseResponse;
    }

    @Override
    @Transactional
    public BaseResponse generalActivity(Long id, String commodyCode, String orderNo) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setOrderNo(orderNo);
        if(activityMapper.updateActivity(id,commodyCode,orderNo)!=1){
            throw new BusinessException("fail","获取库存失败");
        }
        baseResponse.setCode("succ");
        return baseResponse;
    }

    private Activity toActivity(ActivityPojo activityPojo) {
        List<ActivityTagPojo> tags = activityTags.getActivityTags(activityPojo.getActId());
        List<ActivityTask> list= getActivityTasks(activityPojo.getActId());
        List<String> tagsName = new ArrayList<>();
        tags.parallelStream().forEach(tag->{
            tagsName.add(tag.getTag_name());
        });
        return Activity.builder()
                .activityTasks(list)
                .desc(activityPojo.getDesc())
                .end_time(activityPojo.getEnd_time())
                .group_id(activityPojo.getGroup_id())
                .id(activityPojo.getActId())
                .logo_url(activityPojo.getLogo_url())
                .join_cron(activityPojo.getJoin_cron())
                .name(activityPojo.getName())
                .show_time(activityPojo.getShow_time())
                .start_time(activityPojo.getStart_time())
                .tags(tagsName)
                .total_times(activityPojo.getTotal_times())
                .build();
    }

    private List<ActivityTask> getActivityTasks(Long actId){
        List<ActivityTaskPojo> taskList= activityTask.getTaskList(actId);
        List<ActivityTask> list = new ArrayList<>();
        Optional.ofNullable(taskList).ifPresent(tasks-> {
            Collections.sort(tasks, new Comparator<ActivityTaskPojo>() {
                @Override
                public int compare(ActivityTaskPojo task1, ActivityTaskPojo task2) {
                    if (task1.getParent() > task2.getParent())
                        return 1;
                    else
                        return -1;
                }
            });
            Map<Long,ActivityTask> map = new HashMap<>();
            tasks.stream().forEach(task->{
                ActivityTask activityTask = ActivityTask.builder()
                        .task_id(task.getTask_id())
                        .childs(new ArrayList<>())
                        .name(task.getName())
                        .activityAwards(getTaskAwards(task.getTask_id()))
                        .url(task.getUrl()).build();
                if(task.getParent()==0||map.get(task.getParent())==null){
                    map.put(task.getTask_id(),activityTask);
                    list.add(activityTask);
                } else
                    map.get(task.getTask_id()).getChilds().add(activityTask);
            });


        });
        return list;
    }

    private Map<Integer,List<ActivityAward>> getTaskAwards(Long taskId){
        List<ActivityExchangePojo> activityExchangePojos =activityExchange.getExchangeInfoByTask(taskId);
        List<Long> list = new ArrayList<>();
        List<ActivityAward> retList = new ArrayList<>();
        Map<Integer,List<ActivityAward>> map = new HashMap<>();
        Optional.ofNullable(activityExchangePojos).ifPresent(activityExchangePojos1 -> {
            activityExchangePojos1.forEach(activityExchangePojo -> {
                list.add(activityExchangePojo.getAward_id());

            });
        });
        List<AwardInfo> awardInfos = benifitClient.getAwardsById(list);

        Optional.ofNullable(awardInfos).ifPresent(awardInfoList -> {
            awardInfoList.forEach(awardInfo -> {
                awardInfoToActivityAward(activityExchangePojos, awardInfo,map);
            });
        });
        return map;
    }

    private void awardInfoToActivityAward( List<ActivityExchangePojo> activityExchangePojos,AwardInfo awardInfo,Map<Integer,List<ActivityAward>> map){
        activityExchangePojos.parallelStream().forEach(activityExchangePojo -> {
            if(activityExchangePojo.getAward_id().longValue()==awardInfo.getAward_id().longValue()){
                ActivityAward activityAward = ActivityAward.builder()
                        .awardInfo(awardInfo)
                        .ceculatorPrice(ceculator(activityExchangePojo.getCeculator_id(),awardInfo))
                        .number(activityExchangePojo.getCeculator_id()).build();
                if(map.get(activityExchangePojo.getGroup_index())==null){
                    map.put(activityExchangePojo.getGroup_index(),new ArrayList<>());
                }
                map.get(activityExchangePojo.getGroup_index()).add(activityAward);
            }
        });
    }


    private BigDecimal ceculator(int ceculatorId, AwardInfo awardInfo){
        return transCore.ceculator(ceculatorId,awardInfo).getResult();
    }


}
