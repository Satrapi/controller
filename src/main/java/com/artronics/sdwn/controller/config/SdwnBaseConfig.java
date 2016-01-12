package com.artronics.sdwn.controller.config;

import com.artronics.sdwn.controller.map.NetworkMap;
import com.artronics.sdwn.domain.entities.node.Neighbor;
import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import com.artronics.sdwn.domain.entities.packet.PacketEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class SdwnBaseConfig
{
    private final static Logger log = Logger.getLogger(SdwnBaseConfig.class);

    protected String controllerUrl;

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Value("${com.artronics.sdwn.controller.url}")
    public void setControllerUrl(String controllerUrl)
    {
        this.controllerUrl = controllerUrl;
    }

    @Bean(name = "packetQueue")
    public BlockingQueue<PacketEntity> getPacketQueue(){
        return new LinkedBlockingQueue<>();
    }

    @Bean(name = "netMap")
    public Map<Long,NetworkMap<SdwnNodeEntity,Neighbor>> getNetMap(){
        return new HashMap<>();
    }
}
