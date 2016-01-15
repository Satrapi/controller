package com.artronics.sdwn.controller.log;

import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import org.apache.log4j.Logger;

public interface NodeLogger
{
    Logger newNode = Logger.getLogger("com.artronics.sdwn.logger.node.new");
    Logger persistNode = Logger.getLogger("com.artronics.sdwn.logger.node.persist");
    Logger removeNode = Logger.getLogger("com.artronics.sdwn.logger.node.remove");
    Logger changeStatus = Logger.getLogger("com.artronics.sdwn.logger.node.changeStatus");

    void newNode(SdwnNodeEntity node);

    void persistNode(SdwnNodeEntity node);

    void removeNode(SdwnNodeEntity node);

    void changeStatus(SdwnNodeEntity srcNode, SdwnNodeEntity.Status oldStatus,
                      SdwnNodeEntity.Status newStatus);
}
