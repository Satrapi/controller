package com.artronics.sdwn.controller.remote;

import com.artronics.sdwn.controller.map.MapUpdater;
import com.artronics.sdwn.controller.map.NetworkMap;
import com.artronics.sdwn.domain.entities.DeviceConnectionEntity;
import com.artronics.sdwn.domain.entities.SdwnControllerEntity;
import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import com.artronics.sdwn.domain.repositories.DeviceConnectionRepo;
import com.artronics.sdwn.domain.repositories.NodeRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class DeviceRegistrationServiceImpl implements DeviceRegistrationService
{
    private final static Logger log = Logger.getLogger(DeviceRegistrationServiceImpl.class);

    private SdwnControllerEntity controllerEntity;

    private DeviceConnectionRepo deviceRepo;

    private NodeRepo nodeRepo;

    private Map<Long,NetworkMap<SdwnNodeEntity>> netMap;

    private MapUpdater mapUpdater;

    @Override
    public DeviceConnectionEntity registerDevice(DeviceConnectionEntity device, SdwnNodeEntity sink)
    {
        return persistDeviceAndSink(device,sink);
    }

    @Transactional
    private DeviceConnectionEntity persistDeviceAndSink(DeviceConnectionEntity device,
                                                        SdwnNodeEntity sink)
    {
        log.debug("Registering new DeviceConnection: "+device.toString());
        DeviceConnectionEntity persistedDev;

        DeviceConnectionEntity dev = deviceRepo.findByUrl(device.getUrl());

        if (dev == null) {
            log.debug("No DeviceConnection found. Creating one.");
            persistedDev = deviceRepo.create(device, controllerEntity);

            sink = device.getSinkNode();
            sink.setDevice(persistedDev);

            sink = nodeRepo.persist(sink);

            persistedDev.setSinkNode(sink);
            deviceRepo.save(persistedDev);
        }else {
            log.debug("Found DeviceConnection. Updating ");
            dev.setSdwnController(controllerEntity);
            sink = dev.getSinkNode();
            persistedDev = deviceRepo.create(dev, controllerEntity);
        }

        mapUpdater.addSink(sink);

        log.debug("Device persisted: " + persistedDev.toString());


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

    @Autowired
    public void setNodeRepo(NodeRepo nodeRepo)
    {
        this.nodeRepo = nodeRepo;
    }

    @Resource(name = "netMap")
    public void setNetMap(
            Map<Long, NetworkMap<SdwnNodeEntity>> netMap)
    {
        this.netMap = netMap;
    }

    @Autowired
    public void setMapUpdater(MapUpdater mapUpdater)
    {
        this.mapUpdater = mapUpdater;
    }

}
