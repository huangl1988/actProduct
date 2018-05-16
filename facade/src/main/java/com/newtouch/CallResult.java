package com.newtouch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by steven on 2018/3/15.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CallResult<E> {
    String respCode;
    String respDesc;
    E result;
}
