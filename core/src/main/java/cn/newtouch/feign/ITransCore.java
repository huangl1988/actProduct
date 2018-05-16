package cn.newtouch.feign;

import com.newtouch.CallResult;
import com.newtouch.entity.Activity;
import com.newtouch.entity.AwardInfo;
import com.newtouch.response.BaseResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by steven on 2018/4/3.
 */
public interface ITransCore {

    @RequestLine("POST /orderNo")
    @Headers("Content-Type: application/json")
    BaseResponse getOrderNo(Long userId);

    @RequestLine("GET /orderNo")
    @Headers("Content-Type: application/json")
    CallResult checkOrderNo(String orderNo);

    @RequestLine("POST /ceculator/{id}")
    @Headers("Content-Type: application/json")
    CallResult<BigDecimal> ceculator(@Param("id") int id, AwardInfo awardInfo);


}
