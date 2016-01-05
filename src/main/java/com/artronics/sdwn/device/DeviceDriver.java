package com.artronics.sdwn.device;

import com.artronics.sdwn.exception.DeviceConnectionException;

public interface DeviceDriver
{
    void init();

    void open() throws DeviceConnectionException;
}
