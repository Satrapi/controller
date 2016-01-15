package com.artronics.sdwn.controller.map;

import com.artronics.sdwn.controller.map.graph.GraphDelegator;
import com.artronics.sdwn.controller.map.graph.SdwnGraphDelegator;
import com.artronics.sdwn.domain.entities.node.Neighbor;
import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SdwnNetworkMap implements NetworkMap<SdwnNodeEntity>
{
    protected final ListenableUndirectedWeightedGraph<SdwnNodeEntity, DefaultWeightedEdge> graph =
            new ListenableUndirectedWeightedGraph
                    <SdwnNodeEntity, DefaultWeightedEdge>(DefaultWeightedEdge.class);

    protected final GraphDelegator<SdwnNodeEntity> graphDelegator = new SdwnGraphDelegator(graph);

    @Override
    public void addNode(SdwnNodeEntity node)
    {
        graph.addVertex(node);
    }

    @Override
    public void removeNode(SdwnNodeEntity node)
    {
        graph.removeVertex(node);
    }

    @Override
    public void removeLink(SdwnNodeEntity srcNode, SdwnNodeEntity target)
    {
        graph.removeEdge(srcNode, target);
    }

    @Override
    public void addLink(SdwnNodeEntity source, SdwnNodeEntity target, double weight)
    {
        DefaultWeightedEdge edge = graph.addEdge(source, target);

        if (edge != null) {
            this.graph.setEdgeWeight(edge, weight);
        }
    }

    @Override
    public boolean hasLink(SdwnNodeEntity source, SdwnNodeEntity target)
    {
        return graph.containsEdge(source, target);
    }

    @Override
    public boolean contains(SdwnNodeEntity node)
    {
        return graph.containsVertex(node);
    }

    @Override
    public boolean isIsland(SdwnNodeEntity neighbor)
    {
        return graphDelegator.isIsland(neighbor);
    }

    @Override
    public Set<Neighbor<SdwnNodeEntity>> getNeighbors(SdwnNodeEntity node)
    {
        return graphDelegator.getNeighbors(node);
    }

    @Override
    public List<SdwnNodeEntity> getAllNodes()
    {
        return new ArrayList<>(graph.vertexSet());
    }

    @Override
    public Graph<SdwnNodeEntity, DefaultWeightedEdge> getNetworkGraph()
    {
        return this.graph;
    }

    @Override
    public List<SdwnNodeEntity> getShortestPath(SdwnNodeEntity source, SdwnNodeEntity target)
    {
        return graphDelegator.getShortestPath(source,target);
    }

    @Override
    public String toString()
    {
        return super.toString();
    }
}
