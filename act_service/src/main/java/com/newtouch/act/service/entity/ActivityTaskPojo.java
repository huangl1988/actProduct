package com.newtouch.act.service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by steven on 2018/4/14.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ActivityTaskPojo {

    private Long task_id;
    private String name;
    private String url;
    private Long parent;
    private Date create_time;
    private Date update_time;
    private Long activity_id;

}
