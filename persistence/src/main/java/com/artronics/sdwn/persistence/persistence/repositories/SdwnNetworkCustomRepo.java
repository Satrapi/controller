package com.artronics.sdwn.persistence.persistence.repositories;


import com.artronics.sdwn.persistence.entities.SdwnControllerEntity;
import com.artronics.sdwn.persistence.entities.SdwnNetwork;

public interface SdwnNetworkCustomRepo
{
    SdwnNetwork findByIp(String ip);

    SdwnControllerEntity addSdwnController(Long netId, SdwnControllerEntity controller);
}
