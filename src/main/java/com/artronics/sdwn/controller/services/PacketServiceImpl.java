package com.artronics.sdwn.controller.services;

import com.artronics.sdwn.controller.map.WeightCalculator;
import com.artronics.sdwn.domain.entities.node.SdwnNeighbor;
import com.artronics.sdwn.domain.entities.packet.PacketEntity;
import com.artronics.sdwn.domain.entities.packet.SdwnReportPacket;
import com.artronics.sdwn.domain.repositories.NodeRepo;
import com.artronics.sdwn.domain.repositories.PacketRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.BlockingQueue;

@Component
public class PacketServiceImpl implements PacketService
{
    private final static Logger log = Logger.getLogger(PacketServiceImpl.class);

    private BlockingQueue<PacketEntity> packetQueue;

    private WeightCalculator<SdwnNeighbor> weightCalculator;

    private PacketRepo packetRepo;

    private NodeRepo nodeRepo;

    @Override
    public void addPacket(PacketEntity packet)
    {
        switch (packet.getType()){
            case REPORT:
                persistReportPacket((SdwnReportPacket) packet);
                break;
        }


        log.debug("packet added to queue");
        packetQueue.add(packet);
    }

    private SdwnReportPacket persistReportPacket(SdwnReportPacket packet){
        List<SdwnNeighbor> neighbors = packet.getNeighbors();
        neighbors.forEach(neighbor -> neighbor.setWeight(
                weightCalculator.getWeight(packet.getSrcNode(),neighbor)
        ));

        log.debug("Persisting Packet...");
        packetRepo.persist(packet);

        return packet;
    }

    @Autowired
    public void setNodeRepo(NodeRepo nodeRepo)
    {
        this.nodeRepo = nodeRepo;
    }

    @Autowired
    public void setWeightCalculator(
            WeightCalculator<SdwnNeighbor> weightCalculator)
    {
        this.weightCalculator = weightCalculator;
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
