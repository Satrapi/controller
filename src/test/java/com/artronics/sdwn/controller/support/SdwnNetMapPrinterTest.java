package com.artronics.sdwn.controller.support;

import com.artronics.sdwn.controller.map.NetworkMap;
import com.artronics.sdwn.controller.map.SdwnNetworkMap;
import com.artronics.sdwn.domain.entities.DeviceConnectionEntity;
import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import org.junit.Test;

public class SdwnNetMapPrinterTest
{
    private static final NetworkMapPrinter<SdwnNodeEntity> printer= new SdwnNetMapPrinter();

    @Test
    public void just_print_a_netMap_to_see_the_result(){
        DeviceConnectionEntity dev1 = new DeviceConnectionEntity(1L);
        DeviceConnectionEntity dev2 = new DeviceConnectionEntity(2L);
        DeviceConnectionEntity dev3 = new DeviceConnectionEntity(3L);

        SdwnNodeEntity node1 = new SdwnNodeEntity(1L,dev1);
        SdwnNodeEntity node2 = new SdwnNodeEntity(2L,dev2);
        SdwnNodeEntity node3 = new SdwnNodeEntity(3L,dev3);


        SdwnNodeEntity nei80 = new SdwnNodeEntity(80L,dev1);

        SdwnNodeEntity nei4 = new SdwnNodeEntity(4L,dev2);
        SdwnNodeEntity nei5 = new SdwnNodeEntity(5L,dev2);

        NetworkMap<SdwnNodeEntity> map= new SdwnNetworkMap();
        map.addNode(node1);
        map.addNode(node2);
        map.addNode(node3);

        map.addNode(nei4);
        map.addNode(nei5);
        map.addNode(nei80);

        map.addLink(node2,nei4,10);
        map.addLink(node2,nei5,32);

        String s =printer.printNetworkMap(map);
        System.out.println(s);
    }

}