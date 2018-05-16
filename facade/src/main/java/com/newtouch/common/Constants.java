package com.newtouch.common;

/**
 * Created by steven on 2018/4/3.
 */
public final  class Constants {

    public static final String SUCCESS = "succ";

    public static final String MESSAGE="成功";

    public static String PARAM_ERROR ="fail";

    public static String PARAM_MESSAGE ="参数有误";

    enum ActivityRedisKeyEnum{

        RUSH_ACTIVITY_KEY("RUSH_ACTIVITY_{0}");
        private String key;

        ActivityRedisKeyEnum(String key){
            this.key=key;
        }

        public String getRedisKey(String ...keys){
             for(int i=0;i<keys.length;i++){
                 this.key=key.replace("{"+i+"}",keys[i]);
             }
             return key;
        }


    }



}
