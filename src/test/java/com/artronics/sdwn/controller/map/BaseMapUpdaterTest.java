package com.artronics.sdwn.controller.map;

import com.artronics.sdwn.controller.config.SdwnBaseConfig;
import com.artronics.sdwn.controller.map.graph.SdwnGraphDelegator;
import com.artronics.sdwn.controller.support.NetworkMapPrinter;
import com.artronics.sdwn.controller.support.SdwnNetMapPrinter;
import com.artronics.sdwn.controller.testConfig.MockRepoBeanConfig;
import com.artronics.sdwn.domain.entities.node.Neighbor;
import com.artronics.sdwn.domain.entities.node.SdwnNeighbor;
import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import com.artronics.sdwn.domain.entities.packet.SdwnReportPacket;
import com.artronics.sdwn.domain.helpers.FakePacketFactory;
import com.artronics.sdwn.domain.repositories.NodeRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Set;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        BaseMapUpdaterTest.MapUpdaterBeanConfig.class,
        SdwnBaseConfig.class,
        MockRepoBeanConfig.class,
})
public class BaseMapUpdaterTest extends BaseGraphTest
{
    protected static Long fakeId=0L;
    protected FakePacketFactory factory = new FakePacketFactory();
    protected static final NetworkMapPrinter<SdwnNodeEntity> printer= new SdwnNetMapPrinter();

    @Autowired
    @Qualifier("mapUpdaterTest")
    protected MapUpdater mapUpdater;

    @Resource
    @Qualifier("registeredNodes")
    protected Set<SdwnNodeEntity> registeredNodes;

    @Autowired
    @Qualifier("mockNodeRepo")
    protected NodeRepo nodeRepo;

    @Before
    public void setUp() throws Exception
    {
        /*
            Here is Device1 graph created by SeedNetworkGraphAndMap
                sink:0
                  /   \
                w50  w10
                /      \
             135 --w20-- 30
               \         /
               w25    w100
                 \    /
                  136
                   |
                  w30
                   |
                  137
         */
        super.setUp();
        fakeId++;

        graph = this.networkMap.getNetworkGraph();

        graphDelegator = new SdwnGraphDelegator(graph);
    }

    @Test
    public void it_should_add_new_links_based_on_new_report(){
        SdwnReportPacket packet =factory.createReportPacket(fakeId,node137, node0, device, node135);
        registerNodes(packet);
        assertFalse(networkMap.hasLink(node137,node135));

        mapUpdater.updateMap(packet);

        assertTrue(networkMap.hasLink(node137,node135));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void it_should_remove_links_based_on_new_report(){
        SdwnReportPacket packet =factory.createReportPacket(fakeId,node137, node0, device, node135);
        registerNodes(packet);
        assertFalse(networkMap.hasLink(node137,node135));
        assertTrue(networkMap.hasLink(node137,node136));

        mapUpdater.updateMap(packet);

        assertFalse(networkMap.hasLink(node137,node136));
    }
    
    @Test
    public void it_should_update_link(){
        //createReportPacket creates all neighbors with constant weight
        SdwnReportPacket packet =factory.createReportPacket(fakeId,node137, node0, device, node135);
        registerNodesAndUpdateMap(packet);

        Set<Neighbor<SdwnNodeEntity>> neighbors = networkMap.getNeighbors(node137);
        neighbors.forEach(neighbor -> {
            if (neighbor.getNode().equals(node135)){
                assertThat(neighbor.getWeight(),equalTo(FakePacketFactory.WEIGHT));
            }
        });

    }

    @Test
    public void it_should_remove_island_node_form_networkMap(){
        when(nodeRepo.findOne(anyLong())).thenReturn(node136);

        assertTrue(networkMap.contains(node136));
        //first node 30 drops its link with 136
        SdwnReportPacket packet =factory.createReportPacket(fakeId,node30, node0, device, node135,node0);
        registerNodesAndUpdateMap(packet);
        assertTrue(networkMap.contains(node136));
        //then node 137 drops its link with 136
        SdwnReportPacket packet2 =factory.createReportPacket(fakeId,node137, node0, device, node135);
        registerNodesAndUpdateMap(packet2);
        assertTrue(networkMap.contains(node136));
        //then node 135 drops its link with 136
        SdwnReportPacket packet3 =factory.createReportPacket(fakeId,node135, node0, device, node0);
        registerNodesAndUpdateMap(packet3);

        //now node 136 must be island
        assertFalse(networkMap.contains(node136));
    }

    private void registerNodesAndUpdateMap(SdwnReportPacket packet)
    {
        registerNodes(packet);
        mapUpdater.updateMap(packet);
    }

    protected void registerNodes(SdwnReportPacket packet)
    {
        registeredNodes.add(packet.getSrcNode());
        registeredNodes.add(packet.getDstNode());
        packet.getNeighbors().forEach(neighbor ->
                                              registeredNodes.add(neighbor.getNode()));
    }

    @Configuration
    @ComponentScan(basePackages = {"com.artronics.sdwn.controller.log"})
    public static class MapUpdaterBeanConfig
    {
        @Autowired
        @Qualifier("mockNodeRepo")
        NodeRepo mockNodeRepo;

        @Bean(name = "mapUpdaterTest")
        public MapUpdater getMapUpdater()
        {
            MapUpdaterImpl mapU = new MapUpdaterImpl();

            WeightCalculator<SdwnNeighbor> weightCalculator = new FixedWeightCalculator();

            mapU.setNodeRepo(mockNodeRepo);

            return mapU;
        }
    }
}
