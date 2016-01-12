package com.artronics.sdwn.controller.map.graph;


import com.artronics.sdwn.domain.entities.node.Neighbor;
import com.artronics.sdwn.domain.entities.node.SimpleNode;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
public class GraphDelegatorTest
{
//    protected final ListenableUndirectedWeightedGraph<SimpleNode, DefaultWeightedEdge> graph =
//            new ListenableUndirectedWeightedGraph<>(DefaultWeightedEdge.class);
//    GraphDelegator<SimpleNode,> graphHelper = new SdwnGraphDelegator<>(graph);
//
//    SimpleNode node0;
//    SimpleNode node1;
//    SimpleNode node2;
//    SimpleNode node3;
//
//    @Before
//    public void setUp() throws Exception
//    {
//        /*
//            A simple graph for testing GraphHelper
//            node0 --30--> node1
//            node2 --05--> node1
//            node2 --10--> node0
//            node3 --50--> node1
//
//            The shortest path from node0 to node3 is:
//                node0
//                node2
//                node1
//                node3
//            Remember NetworkMap is not a directed graph.
//         */
//
//        node0 = new SimpleNode(0L);
//        node1 = new SimpleNode(1L);
//        node2 = new SimpleNode(2L);
//        node3 = new SimpleNode(3L);
//
//        graph.addVertex(node0);
//        graph.addVertex(node1);
//        graph.addVertex(node2);
//        graph.addVertex(node3);
//
//        DefaultWeightedEdge e;
//
//        e=graph.addEdge(node0,node1);
//        graph.setEdgeWeight(e,30);
//
//        e=graph.addEdge(node2,node1);
//        graph.setEdgeWeight(e,5);
//
//        e=graph.addEdge(node2,node0);
//        graph.setEdgeWeight(e,10);
//
//        e=graph.addEdge(node3,node1);
//        graph.setEdgeWeight(e,50);
//    }
//    @Test
//    public void It_should_give_the_shortest_path()
//    {
//        List<SimpleNode> path = graphHelper.getShortestPath(node0, node3);
//
//        assertThat(path.size(), equalTo(4));
//    }
//
//    @Test
//    public void It_should_give_a_list_of_nodes_rigth_order_contains_source_target()
//    {
//        List<SimpleNode> path = graphHelper.getShortestPath(node0, node3);
//
//        SimpleNode targetNode0 = path.get(0);
//        SimpleNode targetNode2 = path.get(1);
//        SimpleNode targetNode1 = path.get(2);
//        SimpleNode targetNode3 = path.get(3);
//
//        assertThat(targetNode0, equalTo(node0));
//        assertThat(targetNode2, equalTo(node2));
//        assertThat(targetNode1, equalTo(node1));
//        assertThat(targetNode3, equalTo(node3));
//    }
//
//    @Test
//    public void it_should_return_a_set_of_neighbors_not_containing_itself()
//    {
//        Set<SimpleNode> nodes = graphHelper.getNeighbors(node2);
//
//        //should not contain itself
//        assertFalse(nodes.contains(node2));
//
//        assertTrue(nodes.contains(node0));
//        assertTrue(nodes.contains(node1));
//        assertFalse(nodes.contains(node3));
//    }
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