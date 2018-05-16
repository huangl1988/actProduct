package com.newtouch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Created by steven on 2018/2/26.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Commody {

    private BigDecimal price;

    private Integer number;


}
