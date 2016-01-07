package com.artronics.sdwn.controller;

import com.artronics.sdwn.controller.config.HessianRmiConfig;
import com.artronics.sdwn.controller.config.SdwnBeanConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SdwnApplication
{
    public static void main(String[] args)
    {
        SpringApplicationBuilder builder = new SpringApplicationBuilder();
        builder.sources(SdwnApplication.class,
                        SdwnBeanConfig.class,
                        HessianRmiConfig.class)
               .build().run(args);
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
//                SdwnBeanConfig.class,
//                SdwnTaskExecutorConfig.class);
//        context.start();
    }
}
