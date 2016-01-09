package com.artronics.sdwn.controller;

import com.artronics.sdwn.controller.exceptions.DeviceConnectionException;
import com.artronics.sdwn.domain.entities.packet.PacketEntity;

public interface DeviceConnectionService
{
    void init() throws DeviceConnectionException;

    void open();

    void sendPacket(PacketEntity packet);

    void close();
}
