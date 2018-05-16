package com.newtouch.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by steven on 2018/3/15.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Activity {

    private List<ActivityTask> activityTasks;
    private Long id;
    private String name;
    private String desc;
    private String logo_url;
    private Long group_id;
    private Date show_time;
    private Date start_time;
    private Date end_time;
    private int total_times;
    private String join_cron;
    private List<String> tags;
    private String showActRuleUrl;

}
