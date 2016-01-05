package com.artronics.sdwn.config;

import com.artronics.gsdwn.suren.config.SurenBeanConfig;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(SurenBeanConfig.class)
public class SdwnPersistenceConfig
{
    private final static Logger log = Logger.getLogger(SdwnPersistenceConfig.class);
}
