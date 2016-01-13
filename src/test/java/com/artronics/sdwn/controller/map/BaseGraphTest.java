package com.artronics.sdwn.controller.map;

import com.artronics.sdwn.controller.map.graph.GraphDelegator;
import com.artronics.sdwn.controller.map.graph.SdwnGraphDelegator;
import com.artronics.sdwn.controller.support.SeedNetworkGraphAndMap;
import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.junit.Before;

public class BaseGraphTest
{
    protected SeedNetworkGraphAndMap seeder = new SeedNetworkGraphAndMap();
    protected NetworkMap<SdwnNodeEntity> map;
    protected GraphDelegator<SdwnNodeEntity> graphDelegator;
    protected Graph<SdwnNodeEntity, DefaultWeightedEdge> graph;

    protected SdwnNodeEntity node0 = seeder.getSink1();
    protected SdwnNodeEntity node30 = seeder.getSameAddNode1();
    protected SdwnNodeEntity node135 = seeder.getNode135();
    protected SdwnNodeEntity node136 = seeder.getNode136();
    protected SdwnNodeEntity node137 = seeder.getNode137();

    @Before
    public void setUp() throws Exception
    {
        seeder.seed(false);

        map = seeder.getNetworkMap();
        graph = map.getNetworkGraph();

        graphDelegator = new SdwnGraphDelegator(graph);
    }
}
