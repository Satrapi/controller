package com.artronics.sdwn.controller.map.graph;


import com.artronics.sdwn.controller.map.BaseGraphTest;
import com.artronics.sdwn.domain.entities.DeviceConnectionEntity;
import com.artronics.sdwn.domain.entities.node.Neighbor;
import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
public class GraphDelegatorTest extends BaseGraphTest
{

    @Override
    @Before
    public void setUp() throws Exception
    {
        super.setUp();
    }

    @Test
    public void It_should_give_the_shortest_path()
    {
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
        Set<Neighbor<SdwnNodeEntity>> neighbors = graphDelegator.getNeighbors(node135);

        Set<SdwnNodeEntity> nodes = new HashSet<>();
        for (Neighbor<SdwnNodeEntity> neighbor : neighbors) {
            nodes.add(neighbor.getNode());
        }

        //should not contain itself
        assertFalse(nodes.contains(node135));

        assertTrue(nodes.contains(node30));
        assertTrue(nodes.contains(node0));
        assertTrue(nodes.contains(node136));
    }

    @Test
    public void it_should_return_null_if_node_doesnt_exist()
    {
        SdwnNodeEntity node = new SdwnNodeEntity(3432L,new DeviceConnectionEntity(34L));
        assertNull(graphDelegator.getNeighbors(node));
    }

    @Test
    public void it_should_return_empty_set_if_node_has_no_neighbors()
    {
        SdwnNodeEntity node = new SdwnNodeEntity(3432L,seeder.getDevice1());
        graph.addVertex(node);
        Set<Neighbor<SdwnNodeEntity>> nodes = graphDelegator.getNeighbors(node);
        assertThat(nodes.size(), equalTo(0));
    }

    @Test
    public void test_isIsland()
    {
        SdwnNodeEntity node = new SdwnNodeEntity(3432L,seeder.getDevice1());
        graph.addVertex(node);

        assertTrue(graphDelegator.isIsland(node));
        assertFalse(graphDelegator.isIsland(node135));
    }

}