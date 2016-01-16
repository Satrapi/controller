package com.artronics.sdwn.controller.map;

import com.artronics.sdwn.domain.entities.node.Neighbor;
import com.artronics.sdwn.domain.entities.node.SdwnNeighbor;
import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import com.artronics.sdwn.domain.entities.packet.PacketEntity;
import com.artronics.sdwn.domain.entities.packet.SdwnReportPacket;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class MapUpdaterImpl extends AbstractMapUpdater
{
    private final static Logger log = Logger.getLogger(MapUpdaterImpl.class);

    @Override
    public void updateMap(PacketEntity packet)
    {
        if (packet.getId()==null)
            throw new IllegalStateException("Packet must be persisted before processed by MapUpdater");

        //Source of Packet must be active(if it's not already)
        activeNode(packet.getSrcNode());

        switch (packet.getType()) {
            case REPORT:
                processReportPacket((SdwnReportPacket) packet);
                break;
        }
    }

    protected SdwnReportPacket processReportPacket(SdwnReportPacket packet)
    {
        log.debug("Update Map based on "+packet);
        SdwnNodeEntity srcNode = packet.getSrcNode();

        List<SdwnNeighbor> newNeighbors = packet.getNeighbors();
        addNeighborsToMap(newNeighbors);

        Set<Neighbor<SdwnNodeEntity>> oldNeighbors
                = networkMap.getNeighbors(srcNode);

        compareWithCurrentNeighborSet(srcNode, oldNeighbors, newNeighbors);

        return packet;
    }

    private void addNeighborsToMap(List<SdwnNeighbor> newNeighbors)
    {
        newNeighbors.forEach(neighbor -> {
            SdwnNodeEntity node = neighbor.getNode();
            if (!registeredNodes.contains(node))
                throw new IllegalStateException
                        ("There is an unregistered neighbor's node inside report packet: "+node);
            if (!networkMap.contains(node)){
                networkMap.addNode(node);
            }
        });
    }

    private void activeNode(SdwnNodeEntity srcNode)
    {
        if (!networkMap.contains(srcNode) ||
                srcNode.getStatus()!= SdwnNodeEntity.Status.ACTIVE){

            SdwnNodeEntity.Status preS = srcNode.getStatus();
            srcNode.setStatus(SdwnNodeEntity.Status.ACTIVE);
            nodeLogger.changeStatus(srcNode,preS,SdwnNodeEntity.Status.ACTIVE);
            //update db
            nodeRepo.merge(srcNode);
            nodeLogger.persistNode(srcNode);

            networkMap.addNode(srcNode);
            mapLogger.logNewNode(srcNode);
        }
    }

    protected void compareWithCurrentNeighborSet(SdwnNodeEntity srcNode,
                                                 Set<Neighbor<SdwnNodeEntity>> oldNeighbors,
                                                 List<SdwnNeighbor> newNeighbors)
    {
        //Each time a link is updated we remove that link from tempSet
        //What will left is dropped links
        Set<Neighbor<SdwnNodeEntity>> tempSet = new HashSet<>(oldNeighbors);

        newNeighbors.forEach(neighbor -> {
            SdwnNodeEntity targetNode = neighbor.getNode();

            //New neighbor discovery
            if (!networkMap.hasLink(srcNode,targetNode)){
                networkMap.addLink(srcNode,targetNode,neighbor.getWeight());
                mapLogger.logNewLink(srcNode,neighbor);
            }
            //Update Current Link
            else{
                networkMap.addLink(srcNode,targetNode,neighbor.getWeight());
                tempSet.remove(targetNode);
                mapLogger.logUpdatedLink(srcNode,neighbor);
            }
        });

        if (!tempSet.isEmpty()) {
            removeDroppedLinks(srcNode, tempSet);
        }
    }

    protected void removeDroppedLinks(SdwnNodeEntity srcNode,
                                      Set<Neighbor<SdwnNodeEntity>> remainNeighbors)
    {
        remainNeighbors.forEach(neighbor -> {
            SdwnNodeEntity targetNode = neighbor.getNode();

            networkMap.removeLink(srcNode, targetNode);
            mapLogger.removeLink(srcNode,neighbor);

            //Look for other link with this node if
            //there is no link this node just became islan
            //and should be removed from graph
            if (networkMap.isIsland(targetNode)) {
                networkMap.removeNode(targetNode);
                SdwnNodeEntity node =nodeRepo.findOne(targetNode.getId());
                node.setStatus(SdwnNodeEntity.Status.ISLAND);
                nodeRepo.merge(node);
            }
        });
    }

}
