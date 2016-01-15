package com.artronics.sdwn.controller.map;

import com.artronics.sdwn.controller.config.MockRepoBeanConfig;
import com.artronics.sdwn.controller.config.SdwnBaseConfig;
import com.artronics.sdwn.controller.support.FixedWeightCalculator;
import com.artronics.sdwn.domain.entities.DeviceConnectionEntity;
import com.artronics.sdwn.domain.entities.NetworkSession;
import com.artronics.sdwn.domain.entities.SdwnControllerEntity;
import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import com.artronics.sdwn.domain.repositories.NodeRepo;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        BaseMapUpdaterTest.MapUpdaterBeanConfig.class,
        SdwnBaseConfig.class,
        MockRepoBeanConfig.class,
})
@TestPropertySource("classpath:application-defaults-test.properties")
public class BaseMapUpdaterTest
{
    protected static final Long SINK_ADD = 10L;

    protected SdwnControllerEntity controller;
    protected DeviceConnectionEntity device;
    protected SdwnNodeEntity sinkNode = new SdwnNodeEntity(SINK_ADD);

    @Resource
    @Qualifier("registeredNodes")
    protected Set<SdwnNodeEntity> registeredNodes;

    @Autowired
    @Qualifier("mapUpdaterTest")
    protected MapUpdater mapUpdater;

    @Autowired
    @Qualifier("mockNodeRepo")
    protected NodeRepo nodeRepo;

    @Before
    public void setUp() throws Exception
    {
        sinkNode.setId(10L);
        device = new DeviceConnectionEntity(1L,"foo",sinkNode);
    }
    @Configuration
    @ComponentScan(basePackages = {"com.artronics.sdwn.controller.log"})
//    @ComponentScan(basePackages = {"com.artronics.sdwn.controller",
//            "com.artronics.sdwn.domain"},excludeFilters = @ComponentScan.Filter(
//            value= Configuration.class,
//            type = FilterType.ANNOTATION
//    ))

    public static class MapUpdaterBeanConfig{

        NetworkSession networkSession;

        @Autowired
        @Qualifier("mockNodeRepo")
        NodeRepo mockNodeRepo;

        @Autowired
        @Qualifier("fixedWeightCalculator")
        WeightCalculator weightCalculator;

        @Bean(name = "mapUpdaterTest")
        public MapUpdater getMapUpdater(){
            MapUpdaterImpl mapU = new MapUpdaterImpl();

            mapU.setWeightCalculator(weightCalculator);

            mapU.setNodeRepo(mockNodeRepo);

            return mapU;
        }

        @Bean(name = "fixedWeightCalculator")
        public WeightCalculator getWeightCalculator()
        {
            return new FixedWeightCalculator();
        }

        @Bean
        public NetworkSession getNetworkSession()
        {
            NetworkSession session= new NetworkSession();
            session.setId(1000L);
            return session;
        }
    }
}
