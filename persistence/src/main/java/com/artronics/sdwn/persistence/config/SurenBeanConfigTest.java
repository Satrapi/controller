package com.artronics.sdwn.persistence.config;

import com.artronics.sdwn.persistence.persistence.PersistenceConfig;
import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableAutoConfiguration
@Import(PersistenceConfig.class)
@ComponentScan(basePackages = {
        "com.artronics.gsdwn.suren.persistence",
        "com.artronics.gsdwn.suren.services"
})
@PropertySource("classpath:application-test.properties")
public class SurenBeanConfigTest
{
    private final static Logger log = Logger.getLogger(SurenBeanConfigTest.class);
}
