package com.artronics.sdwn.controller.map;

import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import com.artronics.sdwn.domain.entities.packet.PacketEntity;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class MapUpdaterImpl implements MapUpdater
{
    private final static Logger log = Logger.getLogger(MapUpdaterImpl.class);

    private Map<Long,NetworkMap<SdwnNodeEntity>> netMap;

    @Override
    public void addPacket(PacketEntity packet)
    {

    }

    @Resource(name = "netMap")
    public void setNetMap(
            Map<Long, NetworkMap<SdwnNodeEntity>> netMap)
    {
        this.netMap = netMap;
    }

}
