package com.newtouch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.newtouch","cn.newtouch"})
@EnableAutoConfiguration
@EnableTransactionManagement
public class Main
{
    public static void main( String[] args )
    {
        System.setProperty("Log4jContextSelector","org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");
        SpringApplication application = new SpringApplication(Main.class);
        application.run(args);
    }
}
