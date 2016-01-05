package com.artronics.sdwn.controller;

import com.artronics.gsdwn.suren.entities.DeviceConnection;
import com.artronics.gsdwn.suren.entities.packet.PacketEntity;
import com.artronics.gsdwn.suren.persistence.repositories.DeviceConnectionRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SdwnControllerImpl implements SdwnController
{
    private final static Logger log = Logger.getLogger(SdwnControllerImpl.class);

    private DeviceConnection deviceConnection;

    private Long deviceId;
    private DeviceConnectionRepo deviceConnectionRepo;

    @Override
    public void init()
    {
        log.debug("Initializing SDWN-Controller");
    }

    @Override
    public void addPacket(PacketEntity packet)
    {

    }

    @Value("${com.artronics.sdwn.device.id}")
    public void setDeviceId(Long deviceId)
    {
        this.deviceId = deviceId;
    }

    @Autowired
    public void setDeviceConnectionRepo(
            DeviceConnectionRepo deviceConnectionRepo)
    {
        this.deviceConnectionRepo = deviceConnectionRepo;
    }
}
