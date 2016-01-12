package com.artronics.sdwn.controller.map;

import com.artronics.sdwn.domain.entities.DeviceConnectionEntity;
import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class SdwnNodeCompratorTest
{
    DeviceConnectionEntity dev1 = new DeviceConnectionEntity(1L);
    DeviceConnectionEntity dev2 = new DeviceConnectionEntity(2L);

    SdwnNodeEntity n10 = new SdwnNodeEntity(10L);
    SdwnNodeEntity n11 = new SdwnNodeEntity(11L);

    List<SdwnNodeEntity> nodes = new ArrayList<>();

    @Before
    public void setUp() throws Exception
    {
        n10.setDevice(dev1);
        n11.setDevice(dev2);

        nodes.add(n11);
        nodes.add(n10);
    }

    @Test
    public void normal_sort(){

        nodes.sort(new SdwnNodeComprator());

        assertThat(nodes.get(0),equalTo(n10));
    }

    @Test
    public void it_should_ignore_id_if_deviceId_are_same(){
        n11.setDevice(dev1);

        nodes.sort(new SdwnNodeComprator());

        assertThat(nodes.get(0),equalTo(n10));
    }

}