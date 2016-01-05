package com.artronics.sdwn.persistence.persistence.repositories;


import com.artronics.sdwn.persistence.entities.DeviceConnection;

public interface DeviceConnectionCustomRepo
{
    DeviceConnection findByPath(Long networkId, Long controllerId, Long id);
}
