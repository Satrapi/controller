package com.artronics.sdwn.config;

import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
//@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.artronics.sdwn"})
@PropertySource("classpath:application.properties")
public class SdwnBeanConfig
{
    private final static Logger log = Logger.getLogger(SdwnBeanConfig.class);

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
