package com.newtouch.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by steven on 2018/4/4.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Benifit {

    private Long actId;

    private AwardInfo awardInfo;

    private int total;

    private int used;
}
