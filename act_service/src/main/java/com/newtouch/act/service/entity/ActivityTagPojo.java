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
public class ActivityTagPojo {

    private Long id;
    private Long act_id;
    private String tag_name;
    private Date create_time;
    private Date update_time;
    private int agreeTimes;
}
