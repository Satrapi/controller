package com.artronics.sdwn.controller.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

@Configuration
//@ComponentScan(basePackages = "com.artronics.sdwn.domain.helpers")
public class MapSeederRunner implements ApplicationListener<ContextRefreshedEvent>
{
    @Autowired
    private SeedNetworkGraphAndMap seeder;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event)
    {
        seeder.seed(false);
    }

}
