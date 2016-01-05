package com.artronics.sdwn.controller;

import com.artronics.gsdwn.suren.entities.packet.PacketEntity;

public interface SdwnController
{
    void init();

    void addPacket(PacketEntity packet);
}
