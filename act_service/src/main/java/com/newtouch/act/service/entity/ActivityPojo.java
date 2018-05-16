package com.newtouch.act.service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by steven on 2018/4/9.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityPojo {

    private Long actId;
    private String name;
    private String desc;
    private String logo_url;
    private Long group_id;
    private Date show_time;
    private Date start_time;
    private Date end_time;
    private Date create_time;
    private Date update_time;
    private int total_times;
    private Long check_id;
    private String join_cron;
}
