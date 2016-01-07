package com.artronics.sdwn.controller.initializer;

import com.artronics.sdwn.domain.entities.SdwnControllerEntity;
import com.artronics.sdwn.domain.repositories.SdwnControllerRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class SdwnApplicationInitializer implements ApplicationRunner, ApplicationListener<ContextRefreshedEvent>
{
    private final static Logger log = Logger.getLogger(SdwnApplicationInitializer.class);

    private SdwnControllerRepo controllerRepo;

    private String controllerUrl;

    private SdwnControllerEntity controllerEntity;

    @Override
    public void run(ApplicationArguments args) throws Exception
    {
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event)
    {
        log.debug("Looking for Sdwn Controller Entity in DB...");
        controllerEntity = controllerRepo.findByUrl(controllerUrl);

        if (controllerEntity == null) {
            log.debug("There is no Controller with url:"+controllerUrl+"\nAttempt to create one..");
            controllerEntity = new SdwnControllerEntity(controllerUrl);
            controllerEntity.setDescription("Auto created controller during context initialization.");
            controllerEntity.setStatus(SdwnControllerEntity.Status.ACTIVE);
            controllerRepo.save(controllerEntity);
        }
    }

    @Autowired
    public void setControllerRepo(
            SdwnControllerRepo controllerRepo)
    {
        this.controllerRepo = controllerRepo;
    }

    @Value("${com.artronics.sdwn.controller.url}")
    public void setControllerUrl(String controllerUrl)
    {
        this.controllerUrl = controllerUrl;
    }
}
