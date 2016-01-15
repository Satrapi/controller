package com.artronics.sdwn.controller.remote;

import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import com.artronics.sdwn.domain.repositories.NodeRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

@Component
public class NodeRegistrationServiceImpl implements NodeRegistrationService
{
    private final static Logger log = Logger.getLogger(NodeRegistrationServiceImpl.class);

    private Set<SdwnNodeEntity> registeredNodes;

    private NodeRepo nodeRepo;

    @Override
    public SdwnNodeEntity registerNode(SdwnNodeEntity node)
    {
        if (registeredNodes.contains(node))
            throw new IllegalStateException
                    (node+ " is already in map. Attempt to register an already registered node.");

        node.setStatus(SdwnNodeEntity.Status.IDLE);
        nodeRepo.persist(node);

        registeredNodes.add(node);

        log.debug(node+ " registered successfully.");

        return node;
    }

    @Resource
    @Qualifier("registeredNodes")
    public void setRegisteredNodes(
            Set<SdwnNodeEntity> registeredNodes)
    {
        this.registeredNodes = registeredNodes;
    }

    @Autowired
    public void setNodeRepo(NodeRepo nodeRepo)
    {
        this.nodeRepo = nodeRepo;
    }

}
