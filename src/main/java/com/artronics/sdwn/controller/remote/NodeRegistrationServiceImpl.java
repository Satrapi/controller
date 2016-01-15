package com.artronics.sdwn.controller.remote;

import com.artronics.sdwn.controller.map.NetworkMap;
import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import com.artronics.sdwn.domain.repositories.NodeRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NodeRegistrationServiceImpl implements NodeRegistrationService
{
    private final static Logger log = Logger.getLogger(NodeRegistrationServiceImpl.class);

    private NetworkMap<SdwnNodeEntity> networkMap;

    private NodeRepo nodeRepo;

    @Override
    public SdwnNodeEntity registerNode(SdwnNodeEntity node)
    {
        if (networkMap.contains(node))
            throw new IllegalStateException
                    (node+ " is already in map. Attempt to register an already registered node.");

        node.setStatus(SdwnNodeEntity.Status.IDLE);
        nodeRepo.persist(node);

        networkMap.addNode(node);

        log.debug(node+ " registered successfully.");

        return node;
    }

    @Autowired
    public void setNetworkMap(
            NetworkMap<SdwnNodeEntity> networkMap)
    {
        this.networkMap = networkMap;
    }

    @Autowired
    public void setNodeRepo(NodeRepo nodeRepo)
    {
        this.nodeRepo = nodeRepo;
    }

}
