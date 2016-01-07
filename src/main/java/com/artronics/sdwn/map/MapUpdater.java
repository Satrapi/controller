package com.artronics.sdwn.map;

import com.artronics.sdwn.domain.entities.packet.PacketEntity;

public interface MapUpdater
{
    void addPacket(PacketEntity packet);
}
