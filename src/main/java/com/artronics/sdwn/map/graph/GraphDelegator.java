package com.artronics.sdwn.map.graph;

import com.artronics.sdwn.domain.entities.node.Node;
import org.apache.log4j.Logger;
import org.jgrapht.Graph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.*;

public class GraphDelegator<N extends Node>
{
    private final static Logger log = Logger.getLogger(GraphDelegator.class);

    private final Graph<N, DefaultWeightedEdge> graph;

    public GraphDelegator(Graph graph)
    {
        this.graph = graph;
    }

    public List<N> getShortestPath(N source, N target)
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
        Set<N> nodes = new LinkedHashSet<>();

        for (DefaultWeightedEdge link : links) {
            nodes.add(graph.getEdgeSource(link));
            nodes.add(graph.getEdgeTarget(link));
        }
        nodes.remove(source);

        List<N> nodesList = new ArrayList<>(nodes);
        nodesList.add(0, source);

        return nodesList;
    }

    public Set<N> getNeighbors(N node)
    {
        if (!graph.containsVertex(node))
            return null;

        Set<N> nodes = new HashSet<>();

        Set<DefaultWeightedEdge> edges = graph.edgesOf(node);

        for (DefaultWeightedEdge edge : edges) {
            nodes.add(graph.getEdgeSource(edge));
            nodes.add(graph.getEdgeTarget(edge));
        }

        //remove node from set. we just need its neighbors
        nodes.remove(node);

        return nodes;
    }

    public boolean isIsland(N node)
    {
        return graph.edgesOf(node).isEmpty();
    }
}
