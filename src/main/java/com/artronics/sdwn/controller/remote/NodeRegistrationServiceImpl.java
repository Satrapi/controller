package com.artronics.sdwn.controller.remote;

import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class NodeRegistrationServiceImpl implements NodeRegistrationService
{
    private final static Logger log = Logger.getLogger(NodeRegistrationServiceImpl.class);

    @Override
    public SdwnNodeEntity registerNode(SdwnNodeEntity node)
    {
        return null;
    }
}
