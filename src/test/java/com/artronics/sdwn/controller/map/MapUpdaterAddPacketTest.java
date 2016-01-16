package com.artronics.sdwn.controller.map;

import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import com.artronics.sdwn.domain.entities.packet.PacketEntity;
import com.artronics.sdwn.domain.entities.packet.SdwnReportPacket;
import com.artronics.sdwn.domain.helpers.FakePacketFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class MapUpdaterAddPacketTest extends BaseMapUpdaterTest
{
    private FakePacketFactory factory = new FakePacketFactory();

    private NetworkMap<SdwnNodeEntity> map;

    private SdwnNodeEntity node;
    private PacketEntity packet;

    SdwnNodeEntity sink = new SdwnNodeEntity(100L);
    SdwnNodeEntity node35 = new SdwnNodeEntity(35L);
    SdwnNodeEntity node36 = new SdwnNodeEntity(36L);
    SdwnNodeEntity node37 = new SdwnNodeEntity(37L);

    SdwnNodeEntity node40 = new SdwnNodeEntity(40L);

    @Override
    @Before
    public void setUp() throws Exception
    {
        super.setUp();
        node35.setDevice(device);
        node35.setId(35L);
        sink.setDevice(device);
        sink.setId(100L);
        registeredNodes.add(node35);
        registeredNodes.add(sink);
    }

    @Test(expected = IllegalStateException.class)
    public void it_should_throw_exp_if_added_packet_is_not_persisted(){
        SdwnReportPacket packet =factory.createReportPacket(null,node35, sink, device, 10L);
        registerNodes(packet);
        mapUpdater.updateMap(packet);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void it_should_change_status_of_src_node(){
        SdwnReportPacket packet =factory.createReportPacket(fakeId,node35, sink, device, 10L);
        assertTrue(node35.getStatus()!= SdwnNodeEntity.Status.ACTIVE);
        registerNodes(packet);
        mapUpdater.updateMap(packet);

        assertThat(node35.getStatus(),equalTo(SdwnNodeEntity.Status.ACTIVE));
    }

    @Test
    public void it_should_add_src_node_to_networkMap(){
        assertFalse(networkMap.contains(node35));

        node35.setStatus(SdwnNodeEntity.Status.ACTIVE);
        SdwnReportPacket packet =factory.createReportPacket(fakeId,node35, sink, device, 10L);
        registerNodes(packet);
        mapUpdater.updateMap(packet);

        assertTrue(networkMap.contains(node35));
    }
    
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void it_should_add_neighbors_to_networkMap(){
        SdwnReportPacket packet =factory.createReportPacket(fakeId,node35, sink, device, 500L,501L);
        registerNodes(packet);

        assertFalse(networkMap.contains(packet.getNeighbors().get(0).getNode()));
        assertFalse(networkMap.contains(packet.getNeighbors().get(1).getNode()));

        mapUpdater.updateMap(packet);

        assertTrue(networkMap.contains(packet.getNeighbors().get(0).getNode()));
        assertTrue(networkMap.contains(packet.getNeighbors().get(1).getNode()));
    }

    @Test(expected = IllegalStateException.class)
    public void it_should_throw_exp_if_any_neighbor_node_is_not_registered_yet(){
        SdwnReportPacket packet =factory.createReportPacket(null,node35, sink, device, 10L);
        registeredNodes.add(node35);
        registeredNodes.add(sink);
        mapUpdater.updateMap(packet);
    }

}
