package com.artronics.sdwn.controller.map;

import com.artronics.sdwn.domain.entities.packet.PacketEntity;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class PersistenceBasedMapUpdater extends AbstractMapUpdater
{
    private final static Logger log = Logger.getLogger(PersistenceBasedMapUpdater.class);

    @Override
    public void addPacket(PacketEntity packet)
    {
        device = packet.getDevice();

        switch (packet.getType()) {
            case REPORT:
                processReportPacket(packet);
                break;
        }

    }

    private void processReportPacket(PacketEntity packet)
    {

    }

}
