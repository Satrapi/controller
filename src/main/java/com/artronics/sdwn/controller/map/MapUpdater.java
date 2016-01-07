package com.artronics.sdwn.controller.map;

import com.artronics.sdwn.domain.entities.packet.PacketEntity;

public interface MapUpdater
{
    void addPacket(PacketEntity packet);
}
