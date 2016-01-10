package com.artronics.sdwn.controller.config;

import com.artronics.sdwn.controller.map.NetworkMap;
import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import com.artronics.sdwn.domain.entities.packet.PacketEntity;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class SimpleBeanAndResources
{
    private final static Logger log = Logger.getLogger(SimpleBeanAndResources.class);

    @Bean(name = "packetQueue")
    public BlockingQueue<PacketEntity> getPacketQueue(){
        return new LinkedBlockingQueue<>();
    }

    @Bean(name = "netMap")
    public Map<Long,NetworkMap<SdwnNodeEntity>> getNetMap(){
        return new HashMap<>();
    }
}
