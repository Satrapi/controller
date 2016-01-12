package com.artronics.sdwn.controller.map.graph;


import com.artronics.sdwn.controller.map.NetworkMap;
import com.artronics.sdwn.controller.support.SeedNetworkGraph;
import com.artronics.sdwn.domain.entities.node.Neighbor;
import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
public class GraphDelegatorTest
{
    private SeedNetworkGraph seeder = new SeedNetworkGraph();
    private NetworkMap<SdwnNodeEntity> map;
    private GraphDelegator<SdwnNodeEntity> graphDelegator;

    @Before
    public void setUp() throws Exception
    {
        seeder.seed(false);

        map = seeder.getNetworkMap();
        graphDelegator = new SdwnGraphDelegator(map.getNetworkGraph());
    }

    @Test
    public void It_should_give_the_shortest_path()
    {
        SdwnNodeEntity node30 = seeder.getSameAddNode1();
        SdwnNodeEntity node137 = seeder.getNode137();
        List<SdwnNodeEntity> path = graphDelegator.getShortestPath(node30, node137);

        assertThat(path.size(), equalTo(4));
        assertThat(path.get(0).getAddress(),equalTo(30L));
        assertThat(path.get(1).getAddress(),equalTo(135L));
        assertThat(path.get(2).getAddress(),equalTo(136L));
        assertThat(path.get(3).getAddress(),equalTo(137L));
    }

    @Test
    public void it_should_return_a_set_of_neighbors_not_containing_itself()
    {
        SdwnNodeEntity node137 = seeder.getNode137();
        Set<Neighbor<SdwnNodeEntity>> nodes = graphDelegator.getNeighbors(node137);

        //should not contain itself
        assertFalse(nodes.contains(node137));

//        assertTrue(nodes.contains(node0));
//        assertTrue(nodes.contains(node1));
//        assertFalse(nodes.contains(node3));
    }
//
//    @Test
//    public void it_should_return_null_if_node_doesnt_exist()
//    {
//        assertNull(graphHelper.getNeighbors(new SimpleNode(3432L)));
//    }
//
//    @Test
//    public void it_should_return_empty_set_if_node_has_no_neighbors()
//    {
//        SimpleNode node4 = new SimpleNode(4L);
//        graph.addVertex(node4);
//        Set<SimpleNode> nodes = graphHelper.getNeighbors(node4);
//        assertThat(nodes.size(), equalTo(0));
//    }
//
//    @Test
//    public void test_isIsland()
//    {
//        SimpleNode node4 = new SimpleNode(4L);
//        graph.addVertex(node4);
//
//        assertTrue(graphHelper.isIsland(node4));
//        assertFalse(graphHelper.isIsland(node0));
//    }

}