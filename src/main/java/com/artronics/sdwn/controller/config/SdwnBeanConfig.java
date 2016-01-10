package com.artronics.sdwn.controller.config;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.artronics.sdwn.controller",
        "com.artronics.sdwn.domain"})
public class SdwnBeanConfig extends SdwnBaseConfig
{
    private final static Logger log = Logger.getLogger(SdwnBeanConfig.class);



}
