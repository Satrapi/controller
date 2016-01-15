package com.artronics.sdwn.controller.log;

import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import org.springframework.stereotype.Component;

@Component
public class NodeLoggerImpl implements NodeLogger
{
    @Override
    public void newNode(SdwnNodeEntity node)
    {
        newNode.debug("New Node:" +node.toString());
    }

    @Override
    public void persistNode(SdwnNodeEntity node)
    {
        persistNode.debug("Persist :" +node);
    }

    @Override
    public void removeNode(SdwnNodeEntity node)
    {
        removeNode.debug("Remove :" + node.toString());
    }

    @Override
    public void changeStatus(SdwnNodeEntity srcNode, SdwnNodeEntity.Status oldStatus,
                             SdwnNodeEntity.Status newStatus)
    {
        changeStatus.debug("Update "+srcNode + "from "+oldStatus+ "to "+newStatus);
    }
}
