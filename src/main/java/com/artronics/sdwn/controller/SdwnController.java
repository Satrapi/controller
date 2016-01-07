package com.artronics.sdwn.controller;


import com.artronics.sdwn.domain.entities.DeviceConnectionEntity;
import com.artronics.sdwn.domain.entities.packet.PacketEntity;

import java.net.MalformedURLException;

public interface SdwnController
{
    DeviceConnectionEntity registerDeviceConnection(DeviceConnectionEntity device) throws MalformedURLException;

    void addPacket(PacketEntity packet);
}
