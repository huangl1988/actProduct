package com.newtouch.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Created by steven on 2018/4/8.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest implements  IRequest {

    private String orderNo;

    private Long userId;

    private Map<Long,Integer> orderCommodys;

    private Long actId;
}
