package com.artronics.sdwn.controller;


import com.artronics.sdwn.domain.entities.SwitchingNetwork;
import com.artronics.sdwn.domain.entities.packet.PacketEntity;

import java.net.MalformedURLException;

public interface SdwnController
{
    SwitchingNetwork registerSwitchingNetwork(SwitchingNetwork device) throws MalformedURLException;

    void addPacket(PacketEntity packet);
}
