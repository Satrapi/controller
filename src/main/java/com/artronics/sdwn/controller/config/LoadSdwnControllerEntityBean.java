package com.artronics.sdwn.controller.config;

import com.artronics.sdwn.domain.entities.SdwnControllerEntity;
import com.artronics.sdwn.domain.repositories.SdwnControllerRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadSdwnControllerEntityBean extends SdwnBaseConfig
{
    private final static Logger log = Logger.getLogger(LoadSdwnControllerEntityBean.class);

    private SdwnControllerRepo controllerRepo;

    private SdwnControllerEntity controllerEntity;

    @Bean(name = "sdwnControllerEntity")
    public SdwnControllerEntity getControllerEntity(){
        log.debug("Looking for Sdwn Controller Entity in DB...");
        controllerEntity = controllerRepo.findByUrl(controllerUrl);

        if (controllerEntity == null) {
            log.debug("There is no Controller with url:"+controllerUrl+"\nAttempt to create one..");
            controllerEntity = new SdwnControllerEntity(controllerUrl);
            controllerEntity.setDescription("Auto created controller during context initialization.");
            controllerEntity.setStatus(SdwnControllerEntity.Status.ACTIVE);
            controllerRepo.save(controllerEntity);
        }

        else{
            controllerEntity.setStatus(SdwnControllerEntity.Status.ACTIVE);
            controllerRepo.save(controllerEntity);
        }

        log.debug("SdwnController: id: "+controllerEntity.getId()
                          + " and Url: "+controllerEntity.getUrl());
        return controllerEntity;
    }

    @Autowired
    public void setControllerRepo(
            SdwnControllerRepo controllerRepo)
    {
        this.controllerRepo = controllerRepo;
    }
}
