package com.newtouch.act.service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by steven on 2018/4/16.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityExchangePojo {

    private Long exchang_id;
    private Long task_id;
    private Long activity_id;
    private Long award_id;
    private Long number;
    private Date create_time;
    private Date update_time;
    private int group_index;
    private int ceculator_id;

}
