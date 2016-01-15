package com.artronics.sdwn.controller.remote;

import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import com.artronics.sdwn.domain.repositories.NodeRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NodeRegistrationServiceImpl implements NodeRegistrationService
{
    private final static Logger log = Logger.getLogger(NodeRegistrationServiceImpl.class);

    private NodeRepo nodeRepo;

    @Override
    public SdwnNodeEntity registerNode(SdwnNodeEntity node)
    {
        node.setStatus(SdwnNodeEntity.Status.IDLE);
        return nodeRepo.persist(node);
    }

    @Autowired
    public void setNodeRepo(NodeRepo nodeRepo)
    {
        this.nodeRepo = nodeRepo;
    }
}
