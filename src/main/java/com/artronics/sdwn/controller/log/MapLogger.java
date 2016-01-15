package com.artronics.sdwn.controller.log;

import com.artronics.sdwn.domain.entities.node.Neighbor;
import com.artronics.sdwn.domain.entities.node.SdwnNeighbor;
import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import org.apache.log4j.Logger;

public interface MapLogger
{
    Logger newNode = Logger.getLogger("com.artronics.sdwn.logger.map.newNode");
    Logger newLink = Logger.getLogger("com.artronics.sdwn.logger.map.newLink");
    Logger updateLink = Logger.getLogger("com.artronics.sdwn.logger.map.updateLink");
    Logger removeLink = Logger.getLogger("com.artronics.sdwn.logger.map.removeLink");

    void logNewNode(SdwnNodeEntity node);

    void logNewLink(SdwnNodeEntity srcNode, SdwnNeighbor neighbor);

    void logUpdatedLink(SdwnNodeEntity srcNode, SdwnNeighbor neighbor);

    void removeLink(SdwnNodeEntity srcNode, Neighbor<SdwnNodeEntity> neighbor);
}
