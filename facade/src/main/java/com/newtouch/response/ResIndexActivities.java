package com.newtouch.response;

import lombok.*;

import java.util.List;

/**
 * Created by steven on 2018/3/15.
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class ResIndexActivities extends BaseResponse{

    private List<ActivityVo> activityVoList;


}
