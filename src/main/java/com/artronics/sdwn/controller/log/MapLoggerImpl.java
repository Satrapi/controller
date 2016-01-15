package com.artronics.sdwn.controller.log;

import com.artronics.sdwn.domain.entities.node.Neighbor;
import com.artronics.sdwn.domain.entities.node.SdwnNeighbor;
import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import org.springframework.stereotype.Component;

@Component
public class MapLoggerImpl implements MapLogger
{
    @Override
    public void logNewNode(SdwnNodeEntity node)
    {
        newNode.debug("New "+node);
    }

    @Override
    public void logNewLink(SdwnNodeEntity srcNode, SdwnNeighbor neighbor)
    {
        newLink.debug("New Link: "+srcNode+neighbor);
    }

    @Override
    public void logUpdatedLink(SdwnNodeEntity srcNode, SdwnNeighbor neighbor)
    {
        newLink.debug("Updated Link: "+srcNode+neighbor);
    }

    @Override
    public void removeLink(SdwnNodeEntity srcNode, Neighbor<SdwnNodeEntity> neighbor)
    {
        newLink.debug("Removed Link: "+srcNode+neighbor);
    }
}
