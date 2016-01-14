package com.artronics.sdwn.controller.map;

import com.artronics.sdwn.domain.entities.node.Neighbor;
import com.artronics.sdwn.domain.entities.node.SdwnNeighbor;
import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import com.artronics.sdwn.domain.entities.packet.PacketEntity;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Component
//@Primary
public class MapUpdaterImpl extends AbstractMapUpdater
{
    private final static Logger log = Logger.getLogger(MapUpdaterImpl.class);

    @Override
    public PacketEntity addPacket(PacketEntity packet)
    {
        device = packet.getDevice();

        switch (packet.getType()) {
            case REPORT:
                processReportPacket(packet);
                break;
        }

        return packet;
    }

    protected void processReportPacket(PacketEntity packet)
    {
        SdwnNodeEntity srcNode = packet.getSrcNode();

        Set<Neighbor<SdwnNodeEntity>> currentNeighbors
                = networkMap.getNeighbors(srcNode);
        List<SdwnNeighbor> preNeighbors =
                neighborRepo.getNeighbors(srcNode);

        compareWithCurrentNeighborSet(srcNode, currentNeighbors, preNeighbors);

        if (!currentNeighbors.isEmpty()) {
            removeDroppedLinks(srcNode, currentNeighbors);
        }
    }

    protected void compareWithCurrentNeighborSet(SdwnNodeEntity srcNode,
                                                 Set<Neighbor<SdwnNodeEntity>> currentNeighbors,
                                                 List<SdwnNeighbor> preNeighbors)
    {

//        for (Neighbor<> neighbor : report.neighbors) {
//
//            if (!networkMap.contains(neighbor)) {
//                addNode(neighbor);
//                connect(srcNode, neighbor);
//            }else if (currentNeighbors.contains(neighbor)) {
//                currentNeighbors.remove(neighbor);
//                connect(srcNode, neighbor);
//            }else {
//                connect(srcNode, neighbor);
//            }
//        }

    }

    protected void removeDroppedLinks(SdwnNodeEntity srcNode, Set<?> remainNeighbors)
    {
        Iterator it = remainNeighbors.iterator();
        while (it.hasNext()) {
            SdwnNodeEntity nodeEntity = (SdwnNodeEntity) it.next();

//            networkMap.removeLink(srcNode, nodeEntity);
            //Look for other link with this node if
            //there is no link this node just became islan
            //and should be removed from graph
            if (networkMap.isIsland(nodeEntity)) {
                networkMap.removeNode(nodeEntity);
            }

        }
    }

    protected void connect(SdwnNodeEntity node, SdwnNeighbor neighbor)
    {
        double weight = weightCalculator.getWeight(node, neighbor);
//        SdwnNodeEntity n = (SdwnNodeEntity) neighbor ;
//        networkMap.addLink(node, neighbor, weight);
    }

    protected SdwnNodeEntity addNode(SdwnNodeEntity node)
    {
        node = nodeRepo.persist(node);
        networkMap.addNode(node);
        nodeLogger.newNode(node);

        return node;
    }

}
