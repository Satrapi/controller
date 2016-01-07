package com.artronics.sdwn.controller;


import com.artronics.sdwn.domain.entities.SwitchingNetwork;
import com.artronics.sdwn.domain.entities.packet.PacketEntity;

public interface SdwnController
{
    SwitchingNetwork registerSwitchingNetwork(SwitchingNetwork device);

    void addPacket(PacketEntity packet);
}
