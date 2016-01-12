package com.artronics.sdwn.controller.initializer;

import com.artronics.sdwn.domain.entities.NetworkSession;
import com.artronics.sdwn.domain.repositories.NodeRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DbNetworkMapInitializer implements NetworkMapInitializer
{
    private final static Logger log = Logger.getLogger(DbNetworkMapInitializer.class);

    private NodeRepo nodeRepo;

    @Override
    public void initMap(NetworkSession session)
    {

    }

    @Autowired
    public void setNodeRepo(NodeRepo nodeRepo)
    {
        this.nodeRepo = nodeRepo;
    }

}
