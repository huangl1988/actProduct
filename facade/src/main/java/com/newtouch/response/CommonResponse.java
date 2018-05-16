package com.newtouch.response;

import lombok.*;

/**
 * Created by steven on 2018/4/8.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CommonResponse extends BaseResponse {

    protected String code;

    protected String message;

    protected String data;

    protected String orderNo;
}
