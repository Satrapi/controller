package com.artronics.sdwn.controller.remote;

import com.artronics.sdwn.controller.map.MapUpdater;
import com.artronics.sdwn.controller.map.NetworkMap;
import com.artronics.sdwn.domain.entities.DeviceConnectionEntity;
import com.artronics.sdwn.domain.entities.SdwnControllerEntity;
import com.artronics.sdwn.domain.entities.node.Neighbor;
import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import com.artronics.sdwn.domain.repositories.DeviceConnectionRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class DeviceRegistrationServiceImpl implements DeviceRegistrationService
{
    private final static Logger log = Logger.getLogger(DeviceRegistrationServiceImpl.class);

    private SdwnControllerEntity controllerEntity;

    private DeviceConnectionRepo deviceRepo;

    private Map<Long,NetworkMap<SdwnNodeEntity,Neighbor>> netMap;

    private MapUpdater mapUpdater;

    @Override
    public DeviceConnectionEntity register(DeviceConnectionEntity device)
    {
        return persistDevice(device);
    }

    private DeviceConnectionEntity persistDevice(DeviceConnectionEntity device)
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

        mapUpdater.addSink(persistedDev);

        return persistedDev;
    }

    @Autowired
    public void setControllerEntity(
            SdwnControllerEntity controllerEntity)
    {
        this.controllerEntity = controllerEntity;
    }

    @Autowired
    public void setDeviceRepo(DeviceConnectionRepo deviceRepo)
    {
        this.deviceRepo = deviceRepo;
    }

    @Resource(name = "netMap")
    public void setNetMap(
            Map<Long, NetworkMap<SdwnNodeEntity,Neighbor>> netMap)
    {
        this.netMap = netMap;
    }

    @Autowired
    public void setMapUpdater(MapUpdater mapUpdater)
    {
        this.mapUpdater = mapUpdater;
    }

}
