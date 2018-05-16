package com.newtouch.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by steven on 2018/2/26.
 */
@Data
public  class BaseResponse implements Serializable{

    protected String code;

    protected String message;

    protected String data;

    protected String orderNo;

}
