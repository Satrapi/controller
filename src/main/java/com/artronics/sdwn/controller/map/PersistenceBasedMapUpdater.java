package com.artronics.sdwn.controller.map;

import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import com.artronics.sdwn.domain.entities.packet.PacketEntity;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class PersistenceBasedMapUpdater extends MapUpdaterImpl
{
    private final static Logger log = Logger.getLogger(PersistenceBasedMapUpdater.class);

    @Override
    protected void processReportPacket(PacketEntity packet)
    {
        Report report = new Report(packet);
        SdwnNodeEntity srcNode = report.src;

        if (!networkMap.contains(srcNode)) {
            srcNode = addNode(srcNode);
        }

//        Set<SdwnNeighbor> persistedNeighbors = neighborRepo.fetchNeighbors(srcNode);

//        compareWithCurrentNeighborSet(report,srcNode,persistedNeighbors);

//        if (!persistedNeighbors.isEmpty()) {
//            removeDroppedLinks(srcNode,persistedNeighbors);
//        }

        persistNewNeighborSet(srcNode,report);

    }

    private void persistNewNeighborSet(SdwnNodeEntity srcNode, Report report)
    {
//        neighborRepo.persistNeighborSet(srcNode,report.neighbors);
    }

}
