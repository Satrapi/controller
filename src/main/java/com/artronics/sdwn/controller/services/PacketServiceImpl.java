package com.artronics.sdwn.controller.services;

import com.artronics.sdwn.domain.entities.packet.PacketEntity;
import com.artronics.sdwn.domain.repositories.NodeRepo;
import com.artronics.sdwn.domain.repositories.PacketRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.BlockingQueue;

@Component
public class PacketServiceImpl implements PacketService
{
    private final static Logger log = Logger.getLogger(PacketServiceImpl.class);

    private BlockingQueue<PacketEntity> packetQueue;

    private PacketRepo packetRepo;

    private NodeRepo nodeRepo;

    @Override
    public void addPacket(PacketEntity packet)
    {
        log.debug("Persisting Packet...");
        PacketEntity persistedPacket = packetRepo.persist(packet);
        log.debug("packet added to queue");
        packetQueue.add(packet);
    }

    @Autowired
    public void setNodeRepo(NodeRepo nodeRepo)
    {
        this.nodeRepo = nodeRepo;
    }

    @Autowired
    public void setPacketRepo(PacketRepo packetRepo)
    {
        this.packetRepo = packetRepo;
    }

    @Resource(name = "packetQueue")
    public void setPacketQueue(
            BlockingQueue<PacketEntity> packetQueue)
    {
        this.packetQueue = packetQueue;
    }

}
