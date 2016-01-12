package com.artronics.sdwn.controller.map.graph;

import com.artronics.sdwn.domain.entities.node.Neighbor;
import com.artronics.sdwn.domain.entities.node.Node;

import java.util.List;
import java.util.Set;

public interface GraphDelegator<N extends Node>
{
    List<N> getShortestPath(N source, N target);

    Set<Neighbor<N>> getNeighbors(N node);

    boolean isIsland(N neighbor);
}
