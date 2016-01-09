package com.artronics.sdwn.controller.remote;

import com.artronics.sdwn.domain.entities.DeviceConnectionEntity;

public interface DeviceRegistrationService
{
    DeviceConnectionEntity register(DeviceConnectionEntity device);
}
