package com.artronics.sdwn.controller;

import com.artronics.sdwn.domain.entities.packet.PacketEntity;
import com.artronics.sdwn.domain.repositories.PacketRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SdwnControllerImpl implements SdwnController
{
    private final static Logger log = Logger.getLogger(SdwnControllerImpl.class);


    private Long deviceId;
    private PacketRepo packetRepo;

    @Override
    public void init()
    {
        log.debug("Initializing SDWN-Controller");
    }

    @Override
    public void addPacket(PacketEntity packet)
    {
        log.debug("Persisting Packet...");
        PacketEntity persistedPacket = packetRepo.save(packet);

    }

    @Value("${com.artronics.sdwn.device.id}")
    public void setDeviceId(Long deviceId)
    {
        this.deviceId = deviceId;
    }

    @Autowired
    public void setPacketRepo(PacketRepo packetRepo)
    {
        this.packetRepo = packetRepo;
    }
}
