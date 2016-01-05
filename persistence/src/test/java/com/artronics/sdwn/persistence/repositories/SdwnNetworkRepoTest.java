package com.artronics.sdwn.persistence.repositories;

import com.artronics.sdwn.persistence.entities.SdwnControllerEntity;
import com.artronics.sdwn.persistence.entities.SdwnNetwork;
import com.artronics.sdwn.persistence.helper.CreateEntities;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class SdwnNetworkRepoTest extends RepoBaseTes
{
    @Test
    public void test_findByIp()
    {
        SdwnNetwork persistedNet = networkRepo.findByIp(someIp);

        assertNotNull(persistedNet.getId());
        assertThat(persistedNet.getIp(), equalTo(someIp));
    }

    @Test
    @Transactional
    public void addSdwnControllerEntity_should_add_controller(){
        SdwnControllerEntity con = CreateEntities.createCtrl();
        SdwnControllerEntity controller= networkRepo.addSdwnController(netId,con);

        assertNotNull(controller.getId());
        assertNotNull(controller.getSdwnNetwork());

        SdwnNetwork net = networkRepo.findOne(netId);
        assertThat(net.getControllers().size(),equalTo(numOfCtrl+1));
    }

    @Test
    public void it_should_EAGER_ly_fetch_associated_controllers()
    {
        SdwnNetwork persistedNet = networkRepo.findByIp(someIp);

        List<SdwnControllerEntity> controllers = persistedNet.getControllers();

        assertNotNull(controllers);
        assertThat(controllers.size(), equalTo(5));
    }

    //we just test delete
    @Test
    public void it_should_cascade_ALL()
    {
        SdwnNetwork persistedNet = networkRepo.findByIp(someIp);
        //get first controller id
        Long contId = persistedNet.getControllers().get(0).getId();

        networkRepo.delete(persistedNet.getId());

        SdwnControllerEntity ctrl = controllerRepo.findOne(contId);

        assertNull(ctrl);
    }
}