package com.artronics.sdwn.persistence.persistence.repositories.jpa;

import com.artronics.sdwn.persistence.entities.SdwnControllerEntity;
import com.artronics.sdwn.persistence.entities.SdwnNetwork;
import com.artronics.sdwn.persistence.persistence.repositories.SdwnControllerCustomRepo;
import com.artronics.sdwn.persistence.persistence.repositories.SdwnNetworkRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class SdwnControllerRepoImpl implements SdwnControllerCustomRepo
{
    private final static Logger log = Logger.getLogger(SdwnControllerRepoImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private SdwnNetworkRepo networkRepo;

    @Override
    public SdwnControllerEntity findByNetwork(Long networkId, Long id)
    {
        SdwnNetwork network = networkRepo.findOne(networkId);
        if (network == null) {
            return null;
        }

        List<SdwnControllerEntity> controllers = network.getControllers();

        for (SdwnControllerEntity controller:controllers ){
            if (controller.getId().equals(id))
                return controller;
        }

        return null;
    }

    @Override
    public List<SdwnControllerEntity> findByNetwork(Long networkId)
    {
        SdwnNetwork network = networkRepo.findOne(networkId);
        if (network == null) {
            return null;
        }

        List<SdwnControllerEntity> controllers = network.getControllers();

        return controllers;
    }
}
