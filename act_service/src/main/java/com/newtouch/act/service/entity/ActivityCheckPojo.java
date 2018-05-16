package com.newtouch.act.service.entity;

import java.util.Date;

/**
 * Created by steven on 2018/4/16.
 */
public class ActivityCheckPojo {

    private Long check_id;
    private String name;
    private String key;
    private String number;
    private byte limit_user_flag;
    private byte limit_act_flag;
    private Date limit_time_start;
    private Date limit_time_end;
    private String time_cron;

}
