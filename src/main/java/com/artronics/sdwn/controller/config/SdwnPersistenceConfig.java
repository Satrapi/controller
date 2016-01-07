package com.artronics.sdwn.controller.config;

import com.artronics.sdwn.domain.config.SdwnDomainConfig;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(SdwnDomainConfig.class)
public class SdwnPersistenceConfig
{
    private final static Logger log = Logger.getLogger(SdwnPersistenceConfig.class);
}
