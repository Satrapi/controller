package com.artronics.sdwn.controller.remote;

import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;

public interface NodeRegistrationService
{
    SdwnNodeEntity registerNode(SdwnNodeEntity node);
}
