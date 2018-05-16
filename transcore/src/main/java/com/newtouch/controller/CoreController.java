package com.newtouch.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Created by steven on 2018/2/7.
 */
@RestController
@RequestMapping("/core")
public class CoreController {

    private Random random = new Random();

    private DateTimeFormatter dateTimeFormatter =DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    @RequestMapping(value = "getNo",method = RequestMethod.GET)
    public String getNo(String userId){
        IntStream intStream = random.ints(100000,999999);
        int randomInt = intStream.limit(1).findFirst().getAsInt();
        LocalDateTime date = LocalDateTime.now();
        String time=dateTimeFormatter.format(date);
        String no=time.concat(String.valueOf(randomInt));
        return no;
    }

    @RequestMapping(value = "ceculator",method = RequestMethod.GET)
    public String ceculator(){


        return "no";
    }



}
