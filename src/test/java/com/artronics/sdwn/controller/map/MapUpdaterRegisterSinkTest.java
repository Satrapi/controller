//package com.artronics.sdwn.controller.map;
//
//import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//
//import java.util.List;
//
//import static org.hamcrest.core.IsEqual.equalTo;
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.*;
//
//public class MapUpdaterRegisterSinkTest extends BaseMapUpdaterTest
//{
//
//    @Override
//    @Before
//    public void setUp() throws Exception
//    {
//        super.setUp();
//
//        when(nodeRepo.save(any(SdwnNodeEntity.class))).thenReturn(sinkNode);
//    }
//
//    @Test
//    public void it_should_first_register_a_netMap_for_this_device(){
//        mapUpdater.addSink(device);
//
//        assertNotNull(netMap.get(device.getId()));
//    }
//
//    @Ignore("ignore since we mock nodeRepo and determined what is the save() return")
//    @Test
//    public void it_should_set_node_device_to_passed_device(){
//        mapUpdater.addSink(device);
//
//        SdwnNodeEntity sink = getSink();
//
//        assertEquals(sink.getDevice(),device);
//    }
//
//    @Ignore("ignore since we mock nodeRepo and determined what is the save() return")
//    @Test
//    public void it_should_add_a_sink_node_to_this_map(){
//        mapUpdater.addSink(device);
//
//        SdwnNodeEntity sink = getSink();
//
//        assertThat(sink.getType(),equalTo(SdwnNodeEntity.Type.SINK));
//    }
//
//
//    @Ignore("ignore since we mock nodeRepo and determined what is the save() return")
//    @Test
//    public void it_should_set_status_of_the_node_to_ACTIVE(){
//        mapUpdater.addSink(device);
//
//        SdwnNodeEntity sink = getSink();
//
//        assertThat(sink.getStatus(),equalTo(SdwnNodeEntity.Status.ACTIVE));
//    }
//
//    @Test
//    public void it_should_persist_created_sink(){
//        reset(nodeRepo);
//        when(nodeRepo.save(any(SdwnNodeEntity.class))).thenReturn(sinkNode);
//        mapUpdater.addSink(device);
//
//        verify(nodeRepo).save(any(SdwnNodeEntity.class));
//    }
//
//    private SdwnNodeEntity getSink(){
//        NetworkMap networkMap =netMap.get(device.getId());
//
//        List<SdwnNodeEntity> nodes = networkMap.getAllNodes();
//        SdwnNodeEntity sink = nodes.get(0);
//
//        return sink;
//    }
//
//}