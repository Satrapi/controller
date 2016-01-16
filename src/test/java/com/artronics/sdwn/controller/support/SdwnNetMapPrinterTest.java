package com.artronics.sdwn.controller.support;

import com.artronics.sdwn.controller.map.BaseGraphTest;
import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import org.junit.Test;

public class SdwnNetMapPrinterTest extends BaseGraphTest
{
    private static final NetworkMapPrinter<SdwnNodeEntity> printer= new SdwnNetMapPrinter();

    @Test
    public void just_print_a_netMap_to_see_the_result(){
        String s =printer.printNetworkMap(networkMap);
        System.out.println(s);
    }

    @Test
    public void print_with_specific_device(){
        String s =printer.printNetworkMap(networkMap, seeder.getDevice1());
        System.out.println(s);

    }

}