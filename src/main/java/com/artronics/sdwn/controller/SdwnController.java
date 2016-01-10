package com.artronics.sdwn.controller;


import com.artronics.sdwn.domain.entities.packet.PacketEntity;

public interface SdwnController
{
    void start();

    void addPacket(PacketEntity packet);

    void stop();
}
