package com.artronics.sdwn.controller.services;

import com.artronics.sdwn.controller.map.NetworkMap;
import com.artronics.sdwn.domain.entities.node.SdwnNeighbor;
import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import com.artronics.sdwn.domain.entities.packet.PacketEntity;
import com.artronics.sdwn.domain.repositories.NodeRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

@Component
public class PacketServiceImpl implements PacketService
{
    private final static Logger log = Logger.getLogger(PacketServiceImpl.class);

    private BlockingQueue<PacketEntity> packetQueue;

    private NetworkMap<SdwnNodeEntity> networkMap;

    private NodeRepo nodeRepo;

    @Override
    public void addPacket(PacketEntity packet)
    {
        switch (packet.getType()){
            case REPORT:
                processReportPacket(packet);
                break;
        }
        packetQueue.add(packet);
    }

    private void processReportPacket(PacketEntity packet)
    {
        nodeRepo.persist(packet.getSrcNode());

        Set<SdwnNeighbor> neighbors = packet.getSrcNode().getNeighbors();

        for (SdwnNeighbor neighbor : neighbors) {
            SdwnNodeEntity node = neighbor.getNode();
            if(!networkMap.contains(node)){
                nodeRepo.persist(node);
                networkMap.addNode(node);
            }
        }
    }

    @Autowired
    public void setNetworkMap(
            NetworkMap<SdwnNodeEntity> networkMap)
    {
        this.networkMap = networkMap;
    }

    @Autowired
    public void setNodeRepo(NodeRepo nodeRepo)
    {
        this.nodeRepo = nodeRepo;
    }

    @Resource(name = "packetQueue")
    public void setPacketQueue(
            BlockingQueue<PacketEntity> packetQueue)
    {
        this.packetQueue = packetQueue;
    }

}
