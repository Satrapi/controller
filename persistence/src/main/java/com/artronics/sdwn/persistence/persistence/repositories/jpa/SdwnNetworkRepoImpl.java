package com.artronics.sdwn.persistence.persistence.repositories.jpa;

import com.artronics.sdwn.persistence.entities.SdwnControllerEntity;
import com.artronics.sdwn.persistence.entities.SdwnNetwork;
import com.artronics.sdwn.persistence.persistence.repositories.SdwnNetworkCustomRepo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class SdwnNetworkRepoImpl implements SdwnNetworkCustomRepo
{
    private final static Logger log = Logger.getLogger(SdwnNetworkRepoImpl.class);

    @PersistenceContext
    EntityManager em;

    @Override
    public SdwnNetwork findByIp(String ip)
    {
        Query q = em.createQuery("FROM com.artronics.sdwn.persistence.entities.SdwnNetwork n where " +
                                         "n.ip=?1");
        q.setParameter(1,ip);

        SdwnNetwork singleResult = null;
        try {
            singleResult = (SdwnNetwork) q.getSingleResult();
        }catch (NoResultException e) {
            singleResult = null;
        }

        return singleResult;
    }

    @Override
    public SdwnControllerEntity addSdwnController(Long netId, SdwnControllerEntity con)
    {
        SdwnNetwork net = em.find(SdwnNetwork.class,netId);
        net.addSdwnController(con);

        con.setSdwnNetwork(net);

        em.persist(net);

        return con;
    }
}
