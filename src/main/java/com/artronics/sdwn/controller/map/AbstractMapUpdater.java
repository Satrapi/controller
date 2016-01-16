package com.artronics.sdwn.controller.map;

import com.artronics.sdwn.domain.entities.DeviceConnectionEntity;
import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import com.artronics.sdwn.domain.log.MapLogger;
import com.artronics.sdwn.domain.log.NodeLogger;
import com.artronics.sdwn.domain.repositories.NodeRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.Resource;
import java.util.Set;

public abstract class AbstractMapUpdater implements MapUpdater
{
    protected final static Logger log = Logger.getLogger(AbstractMapUpdater.class);

    @Autowired
    protected NodeLogger nodeLogger;

    @Autowired
    protected MapLogger mapLogger;

    protected Set<SdwnNodeEntity> controllerNodes;

    protected NetworkMap<SdwnNodeEntity> networkMap;

    protected NodeRepo nodeRepo;

    protected DeviceConnectionEntity device;

    @Override
    public SdwnNodeEntity addSink(SdwnNodeEntity sink)
    {
        log.debug("Update NetworkMap. Adding sink node: " + sink + " to " +
                          "NetworkMap.");

        nodeLogger.newNode(sink);
        controllerNodes.add(sink);

        return sink;
    }

    @Resource
    @Qualifier("controllerNodes")
    public void setControllerNodes(
            Set<SdwnNodeEntity> controllerNodes)
    {
        this.controllerNodes = controllerNodes;
    }

    @Autowired
    public void setNetworkMap(NetworkMap<SdwnNodeEntity> networkMap)
    {
        this.networkMap = networkMap;
    }

    @Override
    public NetworkMap<SdwnNodeEntity> getNetworkMap()
    {
        return networkMap;
    }

    @Autowired
    public void setNodeRepo(NodeRepo nodeRepo)
    {
        this.nodeRepo = nodeRepo;
    }

}
