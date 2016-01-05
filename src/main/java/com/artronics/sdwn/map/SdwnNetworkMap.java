package com.artronics.sdwn.map;

import com.artronics.sdwn.models.node.SdwnNode;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SdwnNetworkMap implements NetworkMap<SdwnNode>
{
    protected final ListenableUndirectedWeightedGraph<SdwnNode, DefaultWeightedEdge> graph =
            new ListenableUndirectedWeightedGraph
                    <SdwnNode, DefaultWeightedEdge>(DefaultWeightedEdge.class);

    @Override
    public void addNode(SdwnNode node)
    {
        graph.addVertex(node);
    }

    @Override
    public void removeNode(SdwnNode node)
    {
        graph.removeVertex(node);
    }

    @Override
    public void removeLink(SdwnNode srcSdwnNode, SdwnNode neighbor)
    {
        graph.removeEdge(srcSdwnNode, neighbor);
    }

    @Override
    public void addLink(SdwnNode source, SdwnNode target, double weight)
    {
        DefaultWeightedEdge edge = graph.addEdge(source, target);

        if (edge != null) {
            this.graph.setEdgeWeight(edge, weight);
        }
    }

    @Override
    public boolean hasLink(SdwnNode source, SdwnNode target)
    {
        return graph.containsEdge(source, target);
    }

    @Override
    public boolean contains(SdwnNode node)
    {
        return graph.containsVertex(node);
    }

    @Override
    public List<SdwnNode> getAllNodes()
    {
        return new ArrayList<>(graph.vertexSet());
    }

    @Override
    public Graph<SdwnNode, DefaultWeightedEdge> getNetworkGraph()
    {
        return this.graph;
    }
}
