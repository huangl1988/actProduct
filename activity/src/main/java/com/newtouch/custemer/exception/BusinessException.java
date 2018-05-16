package com.newtouch.custemer.exception;

import lombok.Builder;
import lombok.Data;

/**
 * Created by steven on 2018/4/3.
 */
@Data
@Builder
public class BusinessException extends  RuntimeException{

    private String code;

    private String message;

    public BusinessException(String message,String code){
        this.code=code;
        this.message=message;
    }

    public BusinessException(){
        super();
    }


}
