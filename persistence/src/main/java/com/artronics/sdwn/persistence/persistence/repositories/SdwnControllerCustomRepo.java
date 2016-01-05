package com.artronics.sdwn.persistence.persistence.repositories;

import com.artronics.sdwn.persistence.entities.SdwnControllerEntity;

import java.util.List;

public interface SdwnControllerCustomRepo
{
    SdwnControllerEntity findByNetwork(Long networkId, Long id);

    List<SdwnControllerEntity> findByNetwork(Long networkId);
}
