package cn.newtouch.feign;

import com.newtouch.CallResult;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.Map;
import java.util.Set;

/**
 * Created by steven on 2018/4/3.
 */
public interface IRulesEng {

    @RequestLine("GET /query/{userId}")
    @Headers("Content-Type: application/json")
    CallResult<Map<String,String>> isInConditions(Set<String> set, @Param("userId")String userId);
}
