package com.newtouch.response;

import lombok.*;

/**
 * Created by steven on 2018/4/2.
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class ActivityDetailsRes extends BaseResponse {

    ActivityVo activityVo;


}
