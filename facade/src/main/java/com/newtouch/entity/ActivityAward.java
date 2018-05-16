package com.newtouch.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by steven on 2018/4/19.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityAward {

    private int number;

    private AwardInfo awardInfo;

    private int totalNumber;

    private BigDecimal ceculatorPrice;
}
