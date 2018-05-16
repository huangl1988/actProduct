package com.newtouch.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Created by steven on 2018/4/18.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityTask {

    private Long task_id;
    private String name;
    private String url;
    private List<ActivityTask> childs;
    private Map<Integer,List<ActivityAward>> activityAwards;

}
