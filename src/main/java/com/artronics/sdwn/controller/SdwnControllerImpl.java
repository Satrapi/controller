package com.artronics.sdwn.controller;

import com.artronics.sdwn.domain.entities.SdwnControllerEntity;
import com.artronics.sdwn.domain.entities.packet.PacketEntity;
import com.artronics.sdwn.domain.repositories.DeviceConnectionRepo;
import com.artronics.sdwn.domain.repositories.PacketRepo;
import com.artronics.sdwn.domain.repositories.SdwnControllerRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SdwnControllerImpl implements SdwnController
{
    private final static Logger log = Logger.getLogger(SdwnControllerImpl.class);

    private Map<Long, DeviceConnectionService> devices = new HashMap<>();

    private DeviceConnectionRepo deviceRepo;

    private SdwnControllerEntity controllerEntity;

    private PacketRepo packetRepo;
    private SdwnControllerRepo controllerRepo;

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
    public void setDeviceRepo(DeviceConnectionRepo deviceRepo)
    {
        this.deviceRepo = deviceRepo;
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

    @Override
    public String toString()
    {
        return super.toString();
    }

}
