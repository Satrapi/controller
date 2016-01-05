package com.artronics.sdwn.persistence.repositories;

import com.artronics.sdwn.persistence.entities.SdwnControllerEntity;
import com.artronics.sdwn.persistence.entities.SdwnNetwork;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class SdwnControllerRepoTest extends RepoBaseTes
{
    @Test
    public void it_should_EAGER_ly_load_sdwnNetwork(){
        SdwnControllerEntity ctrl = persistedCtrl;

        assertNotNull(ctrl.getSdwnNetwork());
        assertThat(ctrl.getSdwnNetwork().getIp(),equalTo(someIp));
    }

    @Test
    public void test_findByNetwork_it_should_return_associated_SdwnNetwork(){
        SdwnControllerEntity persistedCnt = controllerRepo.findByNetwork(netId,persistedCtrl.getId());

        assertNotNull(persistedCnt.getSdwnNetwork());
        assertThat(persistedCnt.getSdwnNetwork().getIp(),equalTo(someIp));
    }

    @Test
    public void findByNetwork_should_return_null_if_there_is_no_controller(){
        SdwnControllerEntity persistedCnt = controllerRepo.findByNetwork(netId,3445L);

        assertNull(persistedCnt);
    }

    @Test
    public void findByNetwork_should_return_null_if_there_is_no_such_controller(){
        SdwnControllerEntity persistedCnt = controllerRepo.findByNetwork(netId,2400L);

        assertNull(persistedCnt);
    }

    @Test
    public void findByNetwork_if_there_is_no_network_return_null(){
        SdwnControllerEntity persistedCnt = controllerRepo.findByNetwork(10345L,persistedCtrl.getId());

        assertNull(persistedCnt);
    }

    @Test
    public void findByNetwork_should_return_null_if_SdwnNetwork_id_does_not_match(){
        SdwnNetwork otherNet = new SdwnNetwork("35.66.78.89");
        networkRepo.save(otherNet);
        Long otherNetId = otherNet.getId();

        SdwnControllerEntity persistedCnt = controllerRepo.findByNetwork(otherNetId,persistedCtrl.getId());

        assertNull(persistedCnt);
    }

    @Test
    public void findByNetwork_should_return_all_controllers_if_there_is_no_ctrl_id(){
        List<SdwnControllerEntity> persistedCtrls = controllerRepo.findByNetwork(netId);

        assertNotNull(persistedCtrls);
        assertThat(persistedCtrls.size(),equalTo(5));
    }

    @Test
    public void if_SdwnNetwork_is_not_there_it_should_return_null(){
        //test for findByNetwork with just netId as parameter
        List<SdwnControllerEntity> persistedCtrls = controllerRepo.findByNetwork(3653L);

        assertNull(persistedCtrls);
    }

    @Test
    public void findByNetwork_should_return_empty_list_if_there_is_no_ctrl(){
        SdwnNetwork otherNet = new SdwnNetwork("35.66.78.89");
        networkRepo.save(otherNet);
        Long otherNetId = otherNet.getId();

        List<SdwnControllerEntity> persistedCtrls = controllerRepo.findByNetwork(otherNetId);

        assertThat(persistedCtrls.size(),equalTo(0));
    }

    /*
        RELATIONSHIP
     */
    @Test
    public void it_should_EAGER_ly_fetch_DeviceConnection(){

    }

}