package com.newtouch.act.service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by steven on 2018/4/16.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ActivityUserPojo {

    private Long id;
    private Long act_id;
    private Long user_id;
    private int state;
    private String request_json;
    private Date create_time;
    private Date update_time;

}
