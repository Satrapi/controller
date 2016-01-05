package com.artronics.sdwn.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SdwnControllerImpl implements SdwnController
{
    private final static Logger log = Logger.getLogger(SdwnControllerImpl.class);

    private Long deviceId;

    @Override
    public void init()
    {
        log.debug("Initializing SDWN-Controller");
    }

    @Value("${com.artronics.sdwn.device.id}")
    public void setDeviceId(Long deviceId)
    {
        this.deviceId = deviceId;
    }
}
