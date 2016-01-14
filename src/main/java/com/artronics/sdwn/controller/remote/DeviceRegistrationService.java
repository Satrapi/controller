package com.artronics.sdwn.controller.remote;

import com.artronics.sdwn.domain.entities.DeviceConnectionEntity;
import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;

public interface DeviceRegistrationService
{
    DeviceConnectionEntity registerDevice(DeviceConnectionEntity device, SdwnNodeEntity sink);
}
