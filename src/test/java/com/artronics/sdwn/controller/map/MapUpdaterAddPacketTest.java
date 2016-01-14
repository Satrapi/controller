//package com.artronics.sdwn.controller.map;
//
//import com.artronics.sdwn.domain.entities.DeviceConnectionEntity;
//import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
//import com.artronics.sdwn.domain.entities.packet.PacketEntity;
//import com.artronics.sdwn.domain.helpers.FakePacketFactory;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//import static org.mockito.Mockito.*;
//
//public class MapUpdaterAddPacketTest extends BaseMapUpdaterTest
//{
//    private FakePacketFactory factory = new FakePacketFactory();
//
//    private NetworkMap<SdwnNodeEntity> map;
//
//    private SdwnNodeEntity node;
//    private PacketEntity packet;
//
//    SdwnNodeEntity node30 = new SdwnNodeEntity(30L);
//    SdwnNodeEntity node35 = new SdwnNodeEntity(35L);
//    SdwnNodeEntity node36 = new SdwnNodeEntity(36L);
//    SdwnNodeEntity node37 = new SdwnNodeEntity(37L);
//
//    SdwnNodeEntity node40 = new SdwnNodeEntity(40L);
//
//    @Override
//    @Before
//    public void setUp() throws Exception
//    {
//        super.setUp();
//        when(nodeRepo.save(any(SdwnNodeEntity.class))).thenReturn(sinkNode);
//        mapUpdater.addSink(device);
//    }
//
//    @Test
//    public void if_for_src_add_there_is_no_node_it_should_create_a_node(){
//        addAReport();
//
//        mapUpdater.addPacket(packet);
//
//        assertTrue(map.contains(node));
//    }
//
//    @Test
//    public void if_node_is_already_there_it_should_not_persist_it_again(){
//        reset(nodeRepo);
//        addAReport();
//        mapUpdater.addPacket(packet);
//        addAReport();
//        mapUpdater.addPacket(packet);
//
//        verify(nodeRepo,times(1)).create(any(SdwnNodeEntity.class),eq(device.getId()));
//        verifyNoMoreInteractions(nodeRepo);
//    }
//
//    @Test
//    public void it_should_create_a_graph_based_on_report(){
//        //The process is we send createAReport which has node 30 with
//        //neighbors of 35,36,37 . then we compare created graph with expected
//
//        mockNodeRepo(0,30,35,36,37);
//        NetworkMap<SdwnNodeEntity> expMap = getDefaultNetworkMap();
//
//        addAReport();
//        mapUpdater.addPacket(packet);
//
//    }
//    private void mockNodeRepo(int... addresses){
//        for(int add:addresses){
//            SdwnNodeEntity node = new SdwnNodeEntity(Integer.toUnsignedLong(add));
//            when(nodeRepo.create(eq(node),eq(device.getId()))).thenReturn(node);
//        }
//    }
//
//    private void addAReport(){
//        packet = createReport(30,device,35,36,37);
//    }
//
//    private PacketEntity createReport(int src, DeviceConnectionEntity device,int... nAdd){
//        List<Integer> buff = factory.createRawReportPacket(src,SINK_ADD.intValue(),factory.createNeighbors(nAdd));
//
//        PacketEntity packet = PacketEntity.create(buff,device);
//        packet.setId(1L);
//
//        packet.setDevice(device);
//
//        return packet;
//    }
//
//    private void assertMapEqual(NetworkMap exp, NetworkMap act)
//    {
//        assertEquals(exp.getNetworkGraph().vertexSet(), act.getNetworkGraph().vertexSet());
//        //I use toString because actual equal override is considered as weighted
//        //however here i do not consider weigh of each link
//        //For this to work you should construct your link exactly in order
////        assertEquals(exp.getNetworkGraph().edgeSet().toString(),
////                     act.getNetworkGraph().edgeSet().toString());
//    }
//
//    //this returns a netMap correspond to default ReportPacket
//    //which FakePacketFactory produces
//    private NetworkMap getDefaultNetworkMap()
//    {
//        NetworkMap expMap = new SdwnNetworkMap();
//        expMap.addNode(sinkNode);
//        expMap.addNode(node30);
//        expMap.addNode(node35);
//        expMap.addNode(node36);
//        expMap.addNode(node37);
//        expMap.addLink(node30, node35, 45);
//        expMap.addLink(node30, node36, 45);
//        expMap.addLink(node30, node37, 45);
//        return expMap;
//    }
//}
