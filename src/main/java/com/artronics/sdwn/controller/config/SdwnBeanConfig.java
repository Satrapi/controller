package com.artronics.sdwn.controller.config;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = {"com.artronics.sdwn.domain"},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASPECTJ,
                pattern = "com.artronics.sdwn.domain.helpers.*"),
        })
public class SdwnBeanConfig extends SdwnBaseConfig
{
    private final static Logger log = Logger.getLogger(SdwnBeanConfig.class);



}
