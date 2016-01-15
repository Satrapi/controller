package com.artronics.sdwn.controller.map;

import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import com.artronics.sdwn.domain.entities.packet.PacketEntity;

public interface MapUpdater
{
    SdwnNodeEntity addSink(SdwnNodeEntity sink);

    void updateMap(PacketEntity packet);

    NetworkMap<SdwnNodeEntity> getNetworkMap();
}
