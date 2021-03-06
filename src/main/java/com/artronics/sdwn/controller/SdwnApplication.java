package com.artronics.sdwn.controller;

import com.artronics.sdwn.controller.config.HessianRmiConfig;
import com.artronics.sdwn.controller.config.SdwnBeanConfig;
import com.artronics.sdwn.controller.config.SdwnServletConfig;
import com.artronics.sdwn.controller.config.SdwnWebInitializerConfig;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackages = {"com.artronics.sdwn.controller",
//"com.artronics.sdwn.domain"
})
//@PropertySource("classpath:application.properties")
public class SdwnApplication
{
    public static void main(String[] args)
    {
        SpringApplicationBuilder builder = new SpringApplicationBuilder();
        builder
                .bannerMode(Banner.Mode.OFF)
                .sources(
                        SdwnApplication.class,
                        SdwnBeanConfig.class,
                        HessianRmiConfig.class,
                        SdwnWebInitializerConfig.class,
                        SdwnServletConfig.class
                )
//                .registerShutdownHook(true)
               .build().run(args);
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
//                SdwnBeanConfig.class,
//                SdwnTaskExecutorConfig.class);
//        context.start();
    }
}
