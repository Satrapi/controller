package com.artronics.sdwn.controller.map;

import com.artronics.sdwn.domain.entities.node.Neighbor;
import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import com.artronics.sdwn.domain.entities.packet.PacketEntity;
import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.Set;

//@Component
//@Primary
public class MapUpdaterImpl extends AbstractMapUpdater
{
    private final static Logger log = Logger.getLogger(MapUpdaterImpl.class);

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

    protected void processReportPacket(PacketEntity packet)
    {
        Long deviceId = packet.getDevice().getId();

        Report report = new Report(packet);
        SdwnNodeEntity srcNode = report.src;

        if (!networkMap.contains(srcNode)) {
            srcNode = addNode(srcNode);
        }

        Set<SdwnNodeEntity> currentNeighbors = networkMap.getNeighbors
                (srcNode);

        compareWithCurrentNeighborSet(report,srcNode,currentNeighbors);

        if (!currentNeighbors.isEmpty()) {
            removeDroppedLinks(srcNode,currentNeighbors);
        }
    }

    protected void compareWithCurrentNeighborSet(Report report,
                                                 SdwnNodeEntity srcNode,
                                                 Set<?> currentNeighbors){

        for (Neighbor neighbor : report.neighbors) {

            if (!networkMap.contains(neighbor)) {
                addNode(neighbor);
                connect(srcNode, neighbor);
            }else if (currentNeighbors.contains(neighbor)) {
                currentNeighbors.remove(neighbor);
                connect(srcNode, neighbor);
            }else {
                connect(srcNode, neighbor);
            }
        }

    }

    protected void removeDroppedLinks(SdwnNodeEntity srcNode,Set<?> remainNeighbors){
        Iterator it = remainNeighbors.iterator();
        while (it.hasNext()){
            SdwnNodeEntity nodeEntity = (SdwnNodeEntity) it.next();

            networkMap.removeLink(srcNode, nodeEntity);
            //Look for other link with this node if
            //there is no link this node just became islan
            //and should be removed from graph
            if (networkMap.isIsland(nodeEntity)) {
                networkMap.removeNode(nodeEntity);
            }

        }
    }

    protected void connect(SdwnNodeEntity node, Neighbor neighbor)
    {
        double weight = weightCalculator.getWeight(node, neighbor);
//        SdwnNodeEntity n = (SdwnNodeEntity) neighbor ;
        networkMap.addLink(node, neighbor, weight);
    }

    protected SdwnNodeEntity addNode(SdwnNodeEntity node)
    {
        log.debug("Persisting new Node: " + node.toString());
        node.setSession(session);
        node = nodeRepo.create(node, device.getId());
        networkMap.addNode(node);
        nodeLogger.newNode(node);

        return node;
    }

}
