package com.artronics.sdwn.controller.config;

import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import com.artronics.sdwn.domain.entities.packet.PacketEntity;
import com.artronics.sdwn.domain.map.NetworkMap;
import com.artronics.sdwn.domain.map.SdwnNetworkMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class SdwnBaseConfig
{
    private final static Logger log = Logger.getLogger(SdwnBaseConfig.class);

    protected String controllerUrl;

    protected NetworkMap<SdwnNodeEntity> networkMap;

    protected Set<SdwnNodeEntity> controllerNodes = new HashSet<>();

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public NetworkMap<SdwnNodeEntity> getNetworkMap()
    {
        return new SdwnNetworkMap();
    }

    @Bean(name = "controllerNodes")
    public Set<SdwnNodeEntity> getControllerNodes()
    {
        return controllerNodes;
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

}
