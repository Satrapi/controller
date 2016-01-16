package com.artronics.sdwn.controller.map;

import com.artronics.sdwn.controller.log.MapLogger;
import com.artronics.sdwn.controller.log.NodeLogger;
import com.artronics.sdwn.domain.entities.DeviceConnectionEntity;
import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
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

    protected Set<SdwnNodeEntity> registeredNodes;

    protected NetworkMap<SdwnNodeEntity> networkMap;

    protected NodeRepo nodeRepo;

    protected DeviceConnectionEntity device;

    @Override
    public SdwnNodeEntity addSink(SdwnNodeEntity sink)
    {
        log.debug("Update NetworkMap. Adding sink node: " + sink + " to " +
                          "NetworkMap.");

        nodeLogger.newNode(sink);
        registeredNodes.add(sink);

        return sink;
    }

    @Resource
    @Qualifier("registeredNodes")
    public void setRegisteredNodes(
            Set<SdwnNodeEntity> registeredNodes)
    {
        this.registeredNodes = registeredNodes;
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
