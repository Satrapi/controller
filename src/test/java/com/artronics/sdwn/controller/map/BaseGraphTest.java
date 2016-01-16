package com.artronics.sdwn.controller.map;

import com.artronics.sdwn.controller.config.SdwnBaseConfig;
import com.artronics.sdwn.controller.map.graph.GraphDelegator;
import com.artronics.sdwn.controller.map.graph.SdwnGraphDelegator;
import com.artronics.sdwn.controller.support.SeedNetworkGraphAndMap;
import com.artronics.sdwn.domain.entities.DeviceConnectionEntity;
import com.artronics.sdwn.domain.entities.NetworkSession;
import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BaseGraphTest.BaseGraphConfig.class})
@ComponentScan(basePackages = {"com.artronics.sdwn.controller.support"})
@TestPropertySource("classpath:application-defaults-test.properties")
public class BaseGraphTest
{
    @Autowired
    protected SeedNetworkGraphAndMap seeder;

    protected NetworkMap<SdwnNodeEntity> networkMap;
    protected GraphDelegator<SdwnNodeEntity> graphDelegator;
    protected Graph<SdwnNodeEntity, DefaultWeightedEdge> graph;

    protected DeviceConnectionEntity device;
    protected DeviceConnectionEntity device2;

    protected SdwnNodeEntity node0;
    protected SdwnNodeEntity node30;
    protected SdwnNodeEntity node135;
    protected SdwnNodeEntity node136;
    protected SdwnNodeEntity node137;

    @Before
    public void setUp() throws Exception
    {
        seeder.seed(false);

        networkMap = seeder.getNetworkMap();
        graph = networkMap.getNetworkGraph();

        graphDelegator = new SdwnGraphDelegator(graph);

        device = seeder.getDevice1();
        device2 = seeder.getDevice2();


        node0 = seeder.getSink1();

        node30 = seeder.getSameAddNode1();

        node135 = seeder.getNode135();
        node136 = seeder.getNode136();
        node137 = seeder.getNode137();
    }
    @Test
    public void it(){

    }

    @Configuration
    @Import(SdwnBaseConfig.class)
    protected static class BaseGraphConfig{

        @Autowired
        protected NetworkMap<SdwnNodeEntity> networkMap;

        protected SeedNetworkGraphAndMap seedNetworkGraphAndMap;

        @Bean
        public SeedNetworkGraphAndMap getSeedNetworkGraphAndMap()
        {
            SeedNetworkGraphAndMap seeder = new SeedNetworkGraphAndMap();
            seeder.setNetworkMap(networkMap);

            return seeder;
        }

        @Bean
        public NetworkSession getNetworkSession()
        {
            NetworkSession session= new NetworkSession();
            session.setId(10000L);
            return session;
        }

    }
}
