package com.artronics.sdwn.map;

import com.artronics.sdwn.domain.entities.node.Node;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.List;

public interface NetworkMap<N extends Node>
{
    void addNode(N node);

    void removeNode(N node);

    void addLink(N source, N target, double weight);

    boolean hasLink(N source, N target);

    boolean contains(N node);

    List<N> getAllNodes();

    Graph<N, DefaultWeightedEdge> getNetworkGraph();

    void removeLink(N srcNode, N neighbor);
}
