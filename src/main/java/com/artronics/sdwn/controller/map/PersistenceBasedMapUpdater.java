package com.artronics.sdwn.controller.map;

import com.artronics.sdwn.domain.entities.node.Neighbor;
import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import com.artronics.sdwn.domain.entities.packet.PacketEntity;
import com.artronics.sdwn.domain.repositories.NeighborRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Primary
public class PersistenceBasedMapUpdater extends MapUpdaterImpl
{
    private final static Logger log = Logger.getLogger(PersistenceBasedMapUpdater.class);

    private NeighborRepo neighborRepo;

    @Override
    protected void processReportPacket(PacketEntity packet)
    {
        Report report = new Report(packet);
        SdwnNodeEntity srcNode = report.src;

        if (!networkMap.contains(srcNode)) {
            srcNode = addNode(srcNode);
        }

        Set<Neighbor> persistedNeighbors = neighborRepo.fetchNeighbors(srcNode);

        compareWithCurrentNeighborSet(report,srcNode,persistedNeighbors);

        if (!persistedNeighbors.isEmpty()) {
            removeDroppedLinks(srcNode,persistedNeighbors);
        }

        persistNewNeighborSet(srcNode,report);

    }

    private void persistNewNeighborSet(SdwnNodeEntity srcNode, Report report)
    {
        neighborRepo.persistNeighborSet(srcNode,report.neighbors);
    }


    @Autowired
    public void setNeighborRepo(NeighborRepo neighborRepo)
    {
        this.neighborRepo = neighborRepo;
    }

}
