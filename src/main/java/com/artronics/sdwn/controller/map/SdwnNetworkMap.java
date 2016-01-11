package com.artronics.sdwn.controller.map;

import com.artronics.sdwn.controller.map.graph.GraphDelegator;
import com.artronics.sdwn.domain.entities.node.Node;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class SdwnNetworkMap<N extends Node> implements NetworkMap<N>
{
    protected final ListenableUndirectedWeightedGraph<N, DefaultWeightedEdge> graph =
            new ListenableUndirectedWeightedGraph
                    <N, DefaultWeightedEdge>(DefaultWeightedEdge.class);

    protected final GraphDelegator<N> graphDelegator = new GraphDelegator<>(graph);

    @Override
    public void addNode(N node)
    {
        graph.addVertex(node);
    }

    @Override
    public void removeNode(N node)
    {
        graph.removeVertex(node);
    }

    @Override
    public void removeLink(N srcNode, N neighbor)
    {
        graph.removeEdge(srcNode, neighbor);
    }

    @Override
    public void addLink(N source, N target, double weight)
    {
        DefaultWeightedEdge edge = graph.addEdge(source, target);

        if (edge != null) {
            this.graph.setEdgeWeight(edge, weight);
        }
    }

    @Override
    public boolean hasLink(N source, N target)
    {
        return graph.containsEdge(source, target);
    }

    @Override
    public boolean contains(N node)
    {
        return graph.containsVertex(node);
    }

    @Override
    public boolean isIsland(N neighbor)
    {
        return graphDelegator.isIsland(neighbor);
    }

    @Override
    public Set<N> getNeighbors(N node)
    {
        return graphDelegator.getNeighbors(node);
    }

    @Override
    public List<N> getAllNodes()
    {
        return new ArrayList<>(graph.vertexSet());
    }

    @Override
    public Graph<N, DefaultWeightedEdge> getNetworkGraph()
    {
        return this.graph;
    }
}
