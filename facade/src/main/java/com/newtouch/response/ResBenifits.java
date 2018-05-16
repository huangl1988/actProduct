package com.newtouch.response;

import com.newtouch.entity.Benifit;
import lombok.*;

import java.util.List;

/**
 * Created by steven on 2018/4/4.
 */
@Data
@Builder
public class ResBenifits extends BaseResponse{

    private List<Benifit> benifitList;

}
