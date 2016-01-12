package com.artronics.sdwn.controller.map;

import com.artronics.sdwn.controller.map.graph.GraphDelegator;
import com.artronics.sdwn.controller.map.graph.SdwnGraphDelegator;
import com.artronics.sdwn.controller.support.SeedNetworkGraph;
import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class SdwnNetworkMapTest
{
    private SeedNetworkGraph seeder = new SeedNetworkGraph();
    private NetworkMap<SdwnNodeEntity> map;
    private GraphDelegator<SdwnNodeEntity> graphDelegator;
    private Graph<SdwnNodeEntity, DefaultWeightedEdge> graph;

    private SdwnNodeEntity node135 = seeder.getNode135();
    private SdwnNodeEntity node136 = seeder.getNode136();
    private SdwnNodeEntity node137 = seeder.getNode137();


    @Before
    public void setUp() throws Exception
    {
        seeder.seed(false);

        map = seeder.getNetworkMap();
        graph = map.getNetworkGraph();

        graphDelegator = new SdwnGraphDelegator(graph);
    }

    @Test
    public void
    If_we_add_link_to_a_node_which_already_exists_it_should_ignore_it()
    {
        //null check on addLink otherwise jgrapht throws exp
        map.addLink(node136, node135, 10);
    }

    @Test
    public void It_should_return_true_if_a_node_hasLink_to_other_node()
    {
        assertTrue(map.hasLink(node135,node136));
        assertTrue(map.hasLink(node136,node135));

        assertFalse(map.hasLink(node135,node137));
    }

    @Test
    public void It_should_return_false_if_we_ask_a_node_hasLink_to_itself()
    {
        assertThat(map.hasLink(node135, node135), equalTo(false));
    }

    @Test
    public void Test_contains_node()
    {
        assertThat(map.contains(node135), equalTo(true));
    }

    @Test
    public void test_remove_node()
    {
        map.removeNode(node137);
        assertFalse(map.contains(node137));
    }

    @Test
    public void test_remove_link(){
        map.removeLink(node136,node137);
        assertFalse(map.hasLink(node137,node136));
    }

    @Test
    public void Two_nodes_with_same_address_are_equal()
    {
        SdwnNodeEntity eqNode135= new SdwnNodeEntity(135L,seeder.getDevice1());
        assertThat(map.contains(eqNode135), equalTo(true));
    }

    @Test
    public void test_getAllNodes()
    {
        List<SdwnNodeEntity> nodes = map.getAllNodes();
        assertThat(nodes.size(), equalTo(9));
    }

}