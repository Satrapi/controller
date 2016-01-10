package com.artronics.sdwn.controller.map;

import com.artronics.sdwn.controller.config.MockRepoBeanConfig;
import com.artronics.sdwn.controller.config.SimpleBeanAndResources;
import com.artronics.sdwn.domain.entities.DeviceConnectionEntity;
import com.artronics.sdwn.domain.entities.SdwnControllerEntity;
import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import com.artronics.sdwn.domain.repositories.NodeRepo;
import org.junit.Before;
import org.junit.Test;
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
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MapUpdaterImplTest.MapUpdaterBeanConfig.class,
        SimpleBeanAndResources.class,
        MockRepoBeanConfig.class})
public class MapUpdaterImplTest
{
    private static final String DEVICE_URL = "foo.device.url";
    private static final String CTRL_URL = "foo.ctrl.url";
    private static final Long SINK_ADD = 10L;

    private SdwnControllerEntity controller;
    private DeviceConnectionEntity device;
    private SdwnNodeEntity sinkNode = new SdwnNodeEntity(SINK_ADD);

    @Autowired
    @Qualifier("mapUpdaterTest")
    private MapUpdater mapUpdater;

    @Resource(name = "netMap")
    private Map<Long,NetworkMap<SdwnNodeEntity>> netMap;

    @Autowired
    @Qualifier("mockNodeRepo")
    private NodeRepo nodeRepo;

    @Before
    public void setUp() throws Exception
    {
        controller = new SdwnControllerEntity(CTRL_URL);
        controller.setId(1L);
        device = new DeviceConnectionEntity(DEVICE_URL);
        device.setId(2L);

        device.setSinkAddress(SINK_ADD);
        device.setSdwnController(controller);
    }

    @Test
    public void it_should_first_register_a_netMap_for_this_device(){
        mapUpdater.addSink(device);

        assertNotNull(netMap.get(device.getId()));
    }

    @Test
    public void it_should_set_node_device_to_passed_device(){
        mapUpdater.addSink(device);

        SdwnNodeEntity sink = getSink();

        assertEquals(sink.getDevice(),device);
    }

    @Test
    public void it_should_add_a_sink_node_to_this_map(){
        mapUpdater.addSink(device);

        SdwnNodeEntity sink = getSink();

        assertThat(sink.getType(),equalTo(SdwnNodeEntity.Type.SINK));
    }


    @Test
    public void it_should_set_status_of_the_node_to_ACTIVE(){
        mapUpdater.addSink(device);

        SdwnNodeEntity sink = getSink();

        assertThat(sink.getStatus(),equalTo(SdwnNodeEntity.Status.ACTIVE));
    }

    @Test
    public void test_addSink(){
        reset(nodeRepo);
        mapUpdater.addSink(device);

        verify(nodeRepo).save(any(SdwnNodeEntity.class));

        NetworkMap map = netMap.get(device.getId());
        assertTrue(map.contains(sinkNode));
    }

    private SdwnNodeEntity getSink(){
        NetworkMap map =netMap.get(device.getId());

        List<SdwnNodeEntity> nodes = map.getAllNodes();
        SdwnNodeEntity sink = nodes.get(0);

        return sink;
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