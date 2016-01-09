package com.artronics.sdwn.controller.services;

import com.artronics.sdwn.domain.entities.packet.PacketEntity;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.BlockingQueue;

@Component
public class PacketServiceImpl implements PacketService
{
    private final static Logger log = Logger.getLogger(PacketServiceImpl.class);

    private BlockingQueue<PacketEntity> packetQueue;

    @Override
    public void addPacket(PacketEntity packet)
    {
        packetQueue.add(packet);
    }

    @Resource
    public void setPacketQueue(
            BlockingQueue<PacketEntity> packetQueue)
    {
        this.packetQueue = packetQueue;
    }

}