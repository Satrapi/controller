package com.artronics.sdwn.controller.initializer;

import org.apache.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class SdwnApplicationInitializer implements ApplicationRunner,
                                                   ApplicationListener<ContextRefreshedEvent>
{
    private final static Logger log = Logger.getLogger(SdwnApplicationInitializer.class);

    @Override
    public void run(ApplicationArguments args) throws Exception
    {
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event)
    {
    }
}
