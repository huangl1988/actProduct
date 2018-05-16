package com.newtouch.response;

import com.newtouch.entity.ActivityTask;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Created by steven on 2018/2/26.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityVo{

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
    private List<String> tags;



}
