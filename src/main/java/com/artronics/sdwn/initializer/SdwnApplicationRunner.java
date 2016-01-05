package com.artronics.sdwn.initializer;

import com.artronics.sdwn.controller.SdwnController;
import com.artronics.sdwn.device.DeviceDriver;
import com.artronics.sdwn.exception.DeviceConnectionException;
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

    private DeviceDriver deviceDriver;
    private SdwnController sdwnController;

    @Override
    public void run(ApplicationArguments args) throws Exception
    {
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event)
    {
        sdwnController.init();

        try {
            deviceDriver.init();
            deviceDriver.open();

        }catch (DeviceConnectionException e) {
            e.printStackTrace();
            log.error("exception on driver");
        }
    }

    @Autowired
    public void setSdwnController(SdwnController sdwnController)
    {
        this.sdwnController = sdwnController;
    }

    @Autowired
    public void setDeviceDriver(DeviceDriver deviceDriver)
    {
        this.deviceDriver = deviceDriver;
    }
}
