package com.artronics.sdwn.controller.config;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
//@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.artronics.sdwn"})
@PropertySource("classpath:application.properties")
@Import(SdwnPersistenceConfig.class)
public class SdwnBeanConfig
{
    private final static Logger log = Logger.getLogger(SdwnBeanConfig.class);

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
