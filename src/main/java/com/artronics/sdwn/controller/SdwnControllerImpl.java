package com.artronics.sdwn.controller;

import com.artronics.sdwn.domain.entities.SdwnControllerEntity;
import com.artronics.sdwn.domain.entities.SwitchingNetwork;
import com.artronics.sdwn.domain.entities.packet.PacketEntity;
import com.artronics.sdwn.domain.repositories.PacketRepo;
import com.artronics.sdwn.domain.repositories.SdwnControllerRepo;
import com.artronics.sdwn.domain.repositories.SwitchingNetRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Component
public class SdwnControllerImpl implements SdwnController
{
    private final static Logger log = Logger.getLogger(SdwnControllerImpl.class);

    private Map<Long,String> devices = new HashMap<>();

    private SwitchingNetRepo netRepo;

    private SdwnControllerEntity controllerEntity;

    private PacketRepo packetRepo;
    private SdwnControllerRepo controllerRepo;


    @Override
    @Transactional
    public SwitchingNetwork registerSwitchingNetwork(SwitchingNetwork device)
    {
        log.debug("Registering new Switching Network Device...");
        SwitchingNetwork persistedDev;

        SwitchingNetwork dev = netRepo.findByUrl(device.getUrl());

        if (dev == null) {
            persistedDev = netRepo.create(device,controllerEntity.getId());
        }
        else {
            dev.setSdwnController(controllerEntity);
            persistedDev=netRepo.create(dev,controllerEntity.getId());
        }

        log.debug("Device persisted: " + persistedDev.toString());

        addToDeviceMap(persistedDev);

        return persistedDev;
    }

    private void addToDeviceMap(SwitchingNetwork device)
    {
        devices.put(device.getId(),device.getUrl());
    }

    @Override
    public void addPacket(PacketEntity packet)
    {
        log.debug("Persisting Packet...");
        PacketEntity persistedPacket = packetRepo.save(packet);

    }

    @Autowired
    public void setControllerEntity(
            SdwnControllerEntity controllerEntity)
    {
        this.controllerEntity = controllerEntity;
    }

    @Autowired
    public void setNetRepo(SwitchingNetRepo netRepo)
    {
        this.netRepo = netRepo;
    }

    @Autowired
    public void setControllerRepo(
            SdwnControllerRepo controllerRepo)
    {
        this.controllerRepo = controllerRepo;
    }

    @Autowired
    public void setPacketRepo(PacketRepo packetRepo)
    {
        this.packetRepo = packetRepo;
    }
}
