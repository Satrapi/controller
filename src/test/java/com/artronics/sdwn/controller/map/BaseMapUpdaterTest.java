package com.artronics.sdwn.controller.map;

import com.artronics.sdwn.controller.config.MockRepoBeanConfig;
import com.artronics.sdwn.controller.config.SdwnBaseConfig;
import com.artronics.sdwn.controller.map.graph.SdwnGraphDelegator;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Set;

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
        super.setUp();
        fakeId++;

        graph = this.networkMap.getNetworkGraph();

        graphDelegator = new SdwnGraphDelegator(graph);
    }
    @Test
    public void it(){

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
//    @ComponentScan(basePackages = {"com.artronics.sdwn.controller",
//            "com.artronics.sdwn.domain"},excludeFilters = @ComponentScan.Filter(
//            value= Configuration.class,
//            type = FilterType.ANNOTATION
//    ))

    public static class MapUpdaterBeanConfig
    {
        @Autowired
        @Qualifier("mockNodeRepo")
        NodeRepo mockNodeRepo;

//        @Autowired
//        @Qualifier("fixedWeightCalculator")
//        WeightCalculator<SdwnNeighbor> weightCalculator;

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
