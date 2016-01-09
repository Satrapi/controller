package com.artronics.sdwn.controller.remote;

import com.artronics.sdwn.domain.entities.DeviceConnectionEntity;
import com.artronics.sdwn.domain.entities.SdwnControllerEntity;
import com.artronics.sdwn.domain.repositories.DeviceConnectionRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeviceRegistrationServiceImpl implements DeviceRegistrationService
{
    private final static Logger log = Logger.getLogger(DeviceRegistrationServiceImpl.class);

    private SdwnControllerEntity controllerEntity;

    private DeviceConnectionRepo deviceRepo;

    @Override
    @Transactional
    public DeviceConnectionEntity register(DeviceConnectionEntity device)
    {
        log.debug("Registering new DeviceConnection: "+device.toString());
        DeviceConnectionEntity persistedDev;

        DeviceConnectionEntity dev = deviceRepo.findByUrl(device.getUrl());

        if (dev == null) {
            persistedDev = deviceRepo.create(device, controllerEntity.getId());
        }else {
            dev.setSdwnController(controllerEntity);
            persistedDev = deviceRepo.create(dev, controllerEntity.getId());
        }

        log.debug("Device persisted: " + persistedDev.toString());

        return persistedDev;
    }

    @Autowired
    public void setControllerEntity(
            SdwnControllerEntity controllerEntity)
    {
        this.controllerEntity = controllerEntity;
    }

}
