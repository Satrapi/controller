package com.artronics.sdwn.controller.map;

import com.artronics.sdwn.controller.map.graph.GraphDelegator;
import com.artronics.sdwn.domain.entities.node.Node;
import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.List;
import java.util.Set;

public interface NetworkMap<N extends Node,M extends N> extends GraphDelegator<N,M>
{
    void addNode(N node);

    void removeNode(N node);

    void addLink(N source, N target, double weight);

    void removeLink(N srcNode, M neighbor);

    boolean hasLink(N source, N target);

    boolean contains(SdwnNodeEntity node);

    List<N> getAllNodes();

    Graph<SdwnNodeEntity, DefaultWeightedEdge> getNetworkGraph();

}
