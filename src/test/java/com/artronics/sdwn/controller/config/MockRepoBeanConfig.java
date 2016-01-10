package com.artronics.sdwn.controller.config;

import com.artronics.sdwn.domain.repositories.DeviceConnectionRepo;
import com.artronics.sdwn.domain.repositories.NodeRepo;
import com.artronics.sdwn.domain.repositories.PacketRepo;
import com.artronics.sdwn.domain.repositories.SdwnControllerRepo;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.mockito.Mockito.mock;

@Configuration
public class MockRepoBeanConfig
{
    private final static Logger log = Logger.getLogger(MockRepoBeanConfig.class);

    @Bean(name = "mockNodeRepo")
    NodeRepo getMockNodeRepo(){
        return mock(NodeRepo.class);
    }

    @Bean(name = "mockControllerRepo")
    SdwnControllerRepo getMockControllerRepo(){
        return mock(SdwnControllerRepo.class);
    }

    @Bean(name = "mockDeviceRepo")
    DeviceConnectionRepo getMockDeviceRepo(){
        return mock(DeviceConnectionRepo.class);
    }

    @Bean(name = "mockPacketRepo")
    PacketRepo getMockPacketRepo(){
        return mock(PacketRepo.class);
    }

    @Bean(name = "mockEntityManager")
    EntityManager getMockEntityManager(){
        return mock(EntityManager.class);
    }

    @Bean(name = "mockEntityManagerFactory")
    EntityManagerFactory getMockEntityManagerFactory(){
        return mock(EntityManagerFactory.class);
    }
}
