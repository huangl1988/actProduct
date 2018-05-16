package com.newtouch.act.service.feignImpl;

import com.newtouch.act.client.feign.IActivityFeign;
import com.newtouch.act.service.service.ActivityService;
import com.newtouch.entity.Activity;
import com.newtouch.response.BaseResponse;
import feign.Headers;
import feign.RequestLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by steven on 2018/4/9.
 */
@Component
public class ActivityFeignImpl implements IActivityFeign{

    @Autowired
    ActivityService activityService;

    @RequestMapping("activities")
    @ResponseBody
    @Override
    public List<Activity> getActivityInUsing(@RequestBody Map<String, String> map) {
        String time = map.get("localtime");
        String actIdStr=map.get("id");
        Long actId=null;
        if(StringUtils.isEmpty(actIdStr)){
            actId=Long.parseLong(actIdStr);
        }
        return activityService.getActivities(time,actId);
    }
    @RequestMapping("/activities/{id}/rush")
    @ResponseBody
    @Override
    public BaseResponse rushActivity(@PathVariable Long id,@RequestBody Map<String, String> map) {

        String commodyCode=map.get("commodyCode");
        String orderNo=map.get("orderNo");

        return activityService.rush(id,commodyCode,orderNo);
    }
    @RequestMapping("POST /activities/{id}/join")
    @ResponseBody
    @Override
    public BaseResponse generalActivity(@PathVariable  Long id,@RequestBody Map<String, String> map) {
        String commodyCode=map.get("commodyCode");
        String orderNo=map.get("orderNo");

        return activityService.generalActivity(id,commodyCode,orderNo);;
    }

    @Override
    public BaseResponse isOrderOverRush(Map<Long, Integer> orderCommodys) {
        return null;
    }

    @Override
    public List<Activity> getMyJoinActivity(String orderNo) {
        return null;
    }
}
