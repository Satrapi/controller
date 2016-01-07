package com.artronics.sdwn.controller.map;

import com.artronics.sdwn.domain.entities.packet.PacketEntity;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class MapUpdaterImpl implements MapUpdater
{
    private final static Logger log = Logger.getLogger(MapUpdaterImpl.class);

//    private NetworkMap<SdwnNodeEntity>

    @Override
    public void addPacket(PacketEntity packet)
    {

    }
}
