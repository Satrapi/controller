package com.artronics.sdwn.initializer;

import com.artronics.sdwn.controller.SdwnController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class SdwnApplicationRunner implements ApplicationRunner,ApplicationListener<ContextRefreshedEvent>
{
    private final static Logger log = Logger.getLogger(SdwnApplicationRunner.class);

    private SdwnController sdwnController;

    @Override
    public void run(ApplicationArguments args) throws Exception
    {
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event)
    {
        sdwnController.init();

    }

    @Autowired
    public void setSdwnController(SdwnController sdwnController)
    {
        this.sdwnController = sdwnController;
    }
}
