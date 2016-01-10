package com.artronics.sdwn.controller.config;

import com.artronics.sdwn.controller.map.NetworkMap;
import com.artronics.sdwn.domain.entities.SdwnControllerEntity;
import com.artronics.sdwn.domain.entities.packet.PacketEntity;
import com.artronics.sdwn.domain.repositories.SdwnControllerRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = {"com.artronics.sdwn.controller",
        "com.artronics.sdwn.domain"})
public class SdwnBeanConfig
{
    private final static Logger log = Logger.getLogger(SdwnBeanConfig.class);

    private String controllerUrl;

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

        log.debug("Found SdwnController: id: "+controllerEntity.getId()
                          + " and Url: "+controllerEntity.getUrl());
        return controllerEntity;
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

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean(name = "packetQueue")
    public BlockingQueue<PacketEntity> getPacketQueue(){
        return new LinkedBlockingQueue<>();
    }

    @Bean(name = "netMap")
    public Map<Long,NetworkMap> getNetMap(){
        return new HashMap<>();
    }
}
