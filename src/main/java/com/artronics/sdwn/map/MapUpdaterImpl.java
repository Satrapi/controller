package com.artronics.sdwn.map;

import com.artronics.gsdwn.suren.entities.packet.PacketEntity;
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
