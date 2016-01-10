package com.artronics.sdwn.controller.log;

import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import org.apache.log4j.Logger;

public interface NodeLogger
{
    Logger newNode = Logger.getLogger("com.artronics.sdwn.logger.node.new");

    void newNode(SdwnNodeEntity node);
}
