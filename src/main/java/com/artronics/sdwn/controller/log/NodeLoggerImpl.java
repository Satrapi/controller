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
    public void removeNode(SdwnNodeEntity node)
    {
        removeNode.debug("Remove Node: " + node.toString());
    }
}
