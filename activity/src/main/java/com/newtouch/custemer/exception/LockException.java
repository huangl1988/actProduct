package com.newtouch.custemer.exception;

/**
 * Created by steven on 2018/4/3.
 */
public class LockException extends RuntimeException {

    public LockException(){
        super();
    }

    public LockException(String message){
        super(message);
    }


}
