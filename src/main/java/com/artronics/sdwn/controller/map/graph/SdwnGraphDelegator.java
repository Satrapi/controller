package com.artronics.sdwn.controller.map.graph;

import com.artronics.sdwn.domain.entities.node.Neighbor;
import com.artronics.sdwn.domain.entities.node.SdwnNeighbor;
import com.artronics.sdwn.domain.entities.node.SdwnNodeEntity;
import org.apache.log4j.Logger;
import org.jgrapht.Graph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.*;

public class SdwnGraphDelegator implements GraphDelegator<SdwnNodeEntity>
{
    private final static Logger log = Logger.getLogger(SdwnGraphDelegator.class);

    private final Graph<SdwnNodeEntity, DefaultWeightedEdge> graph;

    public SdwnGraphDelegator(Graph graph)
    {
        this.graph = graph;
    }

    @Override
    public List<SdwnNodeEntity> getShortestPath(SdwnNodeEntity source, SdwnNodeEntity target)
    {
        DijkstraShortestPath dijkstra = new DijkstraShortestPath(graph, source, target);
        List<DefaultWeightedEdge> links = dijkstra.getPathEdgeList();

        /*
            Dijkstra returns a list of all links but
            we need a list of nodes. A LinkedHashSet
            preserve the ordering of element and also
            ignore duplicated nodes. At the end we remove
            the source from set. This is because in case
            reversed direction from source to target
            we'll get a wrong order. We'll add source to
            final list.
        */
        Set<SdwnNodeEntity> nodes = new LinkedHashSet<>();

        for (DefaultWeightedEdge link : links) {
            nodes.add(graph.getEdgeSource(link));
            nodes.add(graph.getEdgeTarget(link));
        }
        nodes.remove(source);

        List<SdwnNodeEntity> nodesList = new ArrayList<>(nodes);
        nodesList.add(0, source);

        return nodesList;
    }

    @Override
    public Set<Neighbor<SdwnNodeEntity>> getNeighbors(SdwnNodeEntity node)
    {
        if (!graph.containsVertex(node))
            return null;

        Map<SdwnNodeEntity,DefaultWeightedEdge> nodes = new HashMap<>();

        Set<DefaultWeightedEdge> edges = graph.edgesOf(node);

        for (DefaultWeightedEdge edge : edges) {
            SdwnNodeEntity srcNode = graph.getEdgeSource(edge);
            SdwnNodeEntity dstNode = graph.getEdgeTarget(edge);

            nodes.put(srcNode,edge);
            nodes.put(dstNode,edge);
        }

        //remove node from set. we just need its neighbors
        nodes.remove(node);

        Set<Neighbor<SdwnNodeEntity>> neighbors = new HashSet();
        for (SdwnNodeEntity n : nodes.keySet()) {
            DefaultWeightedEdge e = nodes.get(n);
            Neighbor<SdwnNodeEntity> neighbor = new SdwnNeighbor(n,graph.getEdgeWeight(e));
            neighbors.add(neighbor);
        }

        return neighbors;
    }

    @Override
    public boolean isIsland(SdwnNodeEntity node)
    {
        return graph.edgesOf(node).isEmpty();
    }
}
