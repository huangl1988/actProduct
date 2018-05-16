package com.newtouch.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by steven on 2018/4/20.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AwardInfo {

    private Long award_id;

    private String awardName;

    private String url;

    private BigDecimal price;

    private Date avaliableStartTime;

    private Date avaliableEndTime;

    private int  currencyType;
}
