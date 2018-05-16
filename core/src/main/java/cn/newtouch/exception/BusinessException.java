package cn.newtouch.exception;

import lombok.Data;

/**
 * Created by steven on 2018/5/8.
 */
@Data
public class BusinessException extends RuntimeException {

    private String code;

    private String message;

    public BusinessException(String code,String message){
        super();
        this.code=code;
        this.message=message;

    }



}
