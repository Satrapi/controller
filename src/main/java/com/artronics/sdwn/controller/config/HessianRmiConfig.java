package com.artronics.sdwn.controller.config;

import com.artronics.sdwn.controller.SdwnController;
import com.artronics.sdwn.domain.entities.SdwnControllerEntity;
import com.artronics.sdwn.domain.repositories.SdwnControllerRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;

@Configuration
public class HessianRmiConfig
{
    private final static Logger log = Logger.getLogger(HessianRmiConfig.class);

    private String controllerUrl;

    @Autowired
    private SdwnController sdwnController;

    @Autowired
    private SdwnControllerRepo controllerRepo;

    private SdwnControllerEntity controllerEntity;

    @Bean(name = "/sdwnController")
//    @DependsOn("sdwnControllerServiceUrl")
    public HessianServiceExporter pingServiceExport() {
        HessianServiceExporter he = new HessianServiceExporter();
        he.setService(sdwnController);
        he.setServiceInterface(SdwnController.class);
        return he;
    }

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

        return controllerEntity;
    }

    @Value("${com.artronics.sdwn.controller.url}")
    public void setControllerUrl(String controllerUrl)
    {
        this.controllerUrl = controllerUrl;
    }
}
