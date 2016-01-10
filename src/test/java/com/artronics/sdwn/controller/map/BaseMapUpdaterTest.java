package com.artronics.sdwn.controller.map;

import com.artronics.sdwn.controller.config.MockRepoBeanConfig;
import com.artronics.sdwn.controller.config.SimpleBeanAndResources;
import com.artronics.sdwn.domain.entities.DeviceConnectionEntity;
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
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MapUpdaterRegisterSinkTest.MapUpdaterBeanConfig.class,
        SimpleBeanAndResources.class,
        MockRepoBeanConfig.class})
public class BaseMapUpdaterTest
{
    protected static final String DEVICE_URL = "foo.device.url";
    protected static final String CTRL_URL = "foo.ctrl.url";
    protected static final Long SINK_ADD = 10L;

    protected SdwnControllerEntity controller;
    protected DeviceConnectionEntity device;
    protected SdwnNodeEntity sinkNode = new SdwnNodeEntity(SINK_ADD);

    @Autowired
    @Qualifier("mapUpdaterTest")
    protected MapUpdater mapUpdater;

    @Resource(name = "netMap")
    protected Map<Long,NetworkMap<SdwnNodeEntity>> netMap;

    @Autowired
    @Qualifier("mockNodeRepo")
    protected NodeRepo nodeRepo;

    @Before
    public void setUp() throws Exception
    {
        controller = new SdwnControllerEntity(CTRL_URL);
        controller.setId(1L);
        device = new DeviceConnectionEntity(DEVICE_URL);
        device.setId(2L);

        device.setSinkAddress(SINK_ADD);
        device.setSdwnController(controller);

        sinkNode.setDevice(device);
        sinkNode.setId(3L);
    }
    @Configuration
    @ComponentScan(basePackages = {"com.artronics.sdwn.controller",
            "com.artronics.sdwn.domain"},excludeFilters = @ComponentScan.Filter(
            value= Configuration.class,
            type = FilterType.ANNOTATION
    ))

    public static class MapUpdaterBeanConfig{

        @Resource(name = "netMap")
        private Map<Long,NetworkMap<SdwnNodeEntity>> netMap;

        @Autowired
        @Qualifier("mockNodeRepo")
        NodeRepo mockNodeRepo;

        @Bean(name = "mapUpdaterTest")
        public MapUpdater getMapUpdater(){
            MapUpdaterImpl mapU = new MapUpdaterImpl();
            mapU.setNetMap(netMap);

            mapU.setNodeRepo(mockNodeRepo);

            return mapU;
        }
    }
}
