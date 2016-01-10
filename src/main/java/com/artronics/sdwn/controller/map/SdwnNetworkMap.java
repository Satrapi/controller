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
public class SdwnNetworkMap implements NetworkMap<Node>
{
    protected final ListenableUndirectedWeightedGraph<Node, DefaultWeightedEdge> graph =
            new ListenableUndirectedWeightedGraph
                    <Node, DefaultWeightedEdge>(DefaultWeightedEdge.class);

    protected final GraphDelegator<Node> graphDelegator = new GraphDelegator<>(graph);

    @Override
    public void addNode(Node node)
    {
        graph.addVertex(node);
    }

    @Override
    public void removeNode(Node node)
    {
        graph.removeVertex(node);
    }

    @Override
    public void removeLink(Node srcNode, Node neighbor)
    {
        graph.removeEdge(srcNode, neighbor);
    }

    @Override
    public void addLink(Node source, Node target, double weight)
    {
        DefaultWeightedEdge edge = graph.addEdge(source, target);

        if (edge != null) {
            this.graph.setEdgeWeight(edge, weight);
        }
    }

    @Override
    public boolean hasLink(Node source, Node target)
    {
        return graph.containsEdge(source, target);
    }

    @Override
    public boolean contains(Node node)
    {
        return graph.containsVertex(node);
    }

    @Override
    public boolean isIsland(Node neighbor)
    {
        return graphDelegator.isIsland(neighbor);
    }

    @Override
    public Set<Node> getNeighbors(Node node)
    {
        return graphDelegator.getNeighbors(node);
    }

    @Override
    public List<Node> getAllNodes()
    {
        return new ArrayList<>(graph.vertexSet());
    }

    @Override
    public Graph<Node, DefaultWeightedEdge> getNetworkGraph()
    {
        return this.graph;
    }
}
