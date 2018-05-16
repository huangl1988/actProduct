package com.newtouch.custemer.service.impl;

import cn.newtouch.feign.IRulesEng;
import com.newtouch.CallResult;
import com.newtouch.act.client.feign.IActivityFeign;
import com.newtouch.benifit.client.feign.IBenifit;
import com.newtouch.common.CommonCode;
import com.newtouch.common.Constants;
import com.newtouch.custemer.exception.BusinessException;
import com.newtouch.custemer.exception.LockException;
import com.newtouch.custemer.service.BaseService;
import com.newtouch.custemer.service.IndexService;
import com.newtouch.entity.Activity;

import com.newtouch.entity.ActivityTask;
import com.newtouch.entity.Benifit;
import com.newtouch.order.client.feign.IOrder;
import com.newtouch.request.OrderRequest;
import com.newtouch.response.ActivityDetailsRes;
import com.newtouch.response.ActivityVo;
import com.newtouch.response.BaseResponse;
import lombok.experimental.var;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by steven on 2018/3/15.
 */
@Service
public class IndexServiceImpl extends BaseService implements IndexService{

    @Autowired
    IActivityFeign activityFeign;

    @Autowired
    IBenifit benifit;

    @Autowired
    IRulesEng iRulesEng;

    @Autowired
    MapperFactory mapperFactory;

    @Autowired
    IOrder order;

    @Autowired
    Set<HostAndPort>set;

    @Autowired
    JedisPoolConfig jedisPoolConfig;

    @Override
    public List<ActivityVo> getActivities(String userId, String localDate) {
        Map<String,String> map = new HashMap<>();
        map.put("localtime",localDate);
        List<Activity> list=activityFeign.getActivityInUsing(map);
        Set<String> set = getAllConditions(list);
        CallResult<Map<String,String>> callResult= iRulesEng.isInConditions(set,userId);
        isSuccessCall(callResult);
        List<Activity> fliterList=getFlition(callResult.getResult(),list);
        List retList=Collections.EMPTY_LIST;
        fliterList.stream().forEach(activity -> {
            ceculatorTaskComplete(activity,userId);
            retList.add(toActivityVo(activity));
        });

        return retList;
    }

    private Map<Long,String> ceculatorTaskComplete(Activity activity,String userId) {
        HashSet<String> set = new HashSet<>();
        Optional.ofNullable(activity.getActivityTasks()).ifPresent(taskList -> {
            taskList.stream().forEach(task->{
                taskUrl(task,set);
            });
        });
        CallResult<Map<String,String>> callResult=iRulesEng.isInConditions(set,userId);
        isSuccessCall(callResult);
        Map<String,String> map=callResult.getResult();
        Map<Long,String> mapRet = new HashMap<>();
        Optional.ofNullable(activity.getActivityTasks()).ifPresent(taskList -> {
            taskList.stream().forEach(childTask->{
                this.ceculTask(childTask,mapRet,map);
            });
        });
        return mapRet;
    }

    private void taskUrl(ActivityTask task,Set<String> set){
        if(!StringUtils.isEmpty(task.getUrl()))
            set.add(task.getUrl());
        if(task.getChilds()==null){
            return;
        }else
            task.getChilds().forEach(childTask->{
                this.taskUrl(childTask,set);
            });
    }

    private void ceculTask(ActivityTask task,Map<Long,String> mapRet, Map<String,String> map){
        if(map.get(task.getTask_id())!=null){
            mapRet.put(task.getTask_id(),map.get(task.getTask_id()));
        }
        if(task.getChilds()!=null){
            task.getChilds().forEach(childTask->{
                this.ceculTask(childTask,mapRet,map);
            });
        }
    }


    @Override
    public ActivityDetailsRes getDetailsActivity(Long id) {
        Map<String,String> map = new HashMap<>();
        map.put("id",id+"");
        var list =Optional.ofNullable( activityFeign.getActivityInUsing(map)).orElse(new ArrayList<>());
        return ActivityDetailsRes.builder().activityVo(toActivityVo(list.get(0))).build();
    }

    @Override
    public void rushActivity(Long id, String commodyCode, String orderNo) {
        try(JedisCluster jedisCluster = new JedisCluster(set,jedisPoolConfig);) {
            Long value = jedisCluster.setnx(orderNo, "1");
            if (value != 1) {
                throw new LockException("order:".concat(orderNo).concat("has been locked"));
            }
            Map<String,String> map = new HashMap<>();
            map.put("commodyCode",commodyCode);
            map.put("orderNo",orderNo);
            BaseResponse response = activityFeign.rushActivity(id,map);

            if (!response.getCode().equals(Constants.SUCCESS)) {
                throw BusinessException.builder().code(response.getCode()).message(response.getMessage()).build();
            }
            jedisCluster.expire(orderNo,10);
        }catch (IOException t){
            throw new RuntimeException("error",t);
        }
    }

    @Override
    public void generalActivity(Long id, String commodyCode, String orderNo) {
        Map<String,String> map = new HashMap<>();
        map.put("commodyCode",commodyCode);
        map.put("orderNo",orderNo);
        BaseResponse response = activityFeign.generalActivity(id,map);

        if (!response.getCode().equals(Constants.SUCCESS)) {
            throw BusinessException.builder().code(response.getCode()).message(response.getMessage()).build();
        }
    }

    @Override
    public List<ActivityVo> myJoinActivity(String orderNo) {

        List<Activity> list=activityFeign.getMyJoinActivity(orderNo);
        Set<String> set = getAllConditions(list);

        List retList=Collections.EMPTY_LIST;
        list.stream().forEach(activity -> {
            retList.add(toActivityVo(activity));
        });
        return retList;
    }

    @Override
    public List<Benifit> benifits(String userId, String activityId) {
        return benifit.getBenifits(userId);
    }

    @Override
    public BaseResponse createOrder(OrderRequest request) {

        try(JedisCluster jedisCluster = new JedisCluster(set,jedisPoolConfig);) {
            String orderNo=request.getOrderNo();
            Long value = jedisCluster.setnx(orderNo, "2");
            if (value != 1) {
                throw new LockException("order:".concat(orderNo).concat("has been locked"));
            }
            BaseResponse response = order.createOrder(request);

            if (!response.getCode().equals(Constants.SUCCESS)) {
                throw BusinessException.builder().code(response.getCode()).message(response.getMessage()).build();
            }
            jedisCluster.expire(orderNo,10);
            return response;
        }catch (IOException t){
            throw new RuntimeException("error",t);
        }
    }

    private ActivityVo toActivityVo(Activity activity){
        if(activity==null)
            return null;
        MapperFacade mapperFacade=mapperFactory.getMapperFacade();
        return mapperFacade.map(activity,ActivityVo.class);
    }


    private Set<String> getAllConditions(List<Activity> list){
        HashSet<String> set = new HashSet<>();
        Optional.ofNullable(list).ifPresent(activities -> {
            activities.forEach(activity -> {
                set.add(activity.getShowActRuleUrl());
            });
        });
        return set;
    }


    private boolean isSuccessCall(CallResult callResult){
        if (CommonCode.SUCCESS_CODE.equals(callResult.getRespCode())){
            return true;
        }
        throw new RuntimeException("");
    }

    private List<Activity> getFlition(Map<String,String> conditionMap,List<Activity> list){

       return list.stream().filter(new Predicate<Activity>() {
            @Override
            public boolean test(Activity activity) {
                return conditionMap.containsKey(activity.getShowActRuleUrl());
            }
        }).collect(Collectors.toList());
    }




}
