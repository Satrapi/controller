package com.artronics.sdwn.controller.integration;

import com.artronics.sdwn.device.controller.DeviceController;
import com.artronics.sdwn.device.controller.DeviceControllerImpl;
import com.artronics.sdwn.domain.config.PersistenceConfigTest;
import com.artronics.sdwn.domain.entities.SdwnControllerEntity;
import com.artronics.sdwn.domain.repositories.NodeRepo;
import com.artronics.sdwn.domain.repositories.SdwnControllerRepo;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.PostConstruct;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        PersistenceConfigTest.class,
        NodeRegistrationIt.DeviceBeansConfig.class,
})
@TestPropertySource("classpath:application-defaults-test.properties")
public class NodeRegistrationIt
{
    private final static Logger log = Logger.getLogger(NodeRegistrationIt.class);

    @Autowired
    private DeviceController deviceController;

    @Autowired
    private NodeRepo nodeRepo;


    @Test
    public void it_should() throws Exception
    {


    }

    @Configuration
    @Import({PersistenceConfigTest.class})
    static class DeviceBeansConfig{

        @Autowired
        private SdwnControllerRepo controllerRepo;

        private DeviceController deviceController;

        private SdwnControllerEntity controllerEntity;

        @PostConstruct
        public void createSingletons(){
            controllerEntity = new SdwnControllerEntity("foo");
            controllerRepo.save(controllerEntity);
        }

        @Bean
        public DeviceController getDeviceController()
        {
            deviceController = new DeviceControllerImpl();
            return deviceController;
        }
    }
}
