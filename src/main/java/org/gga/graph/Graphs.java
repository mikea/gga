package org.gga.graph;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

/**
 * @author mike.aizatsky@gmail.com
 */
public class Graphs {
    public static Set<Edge> getAllEdges(Graph graph) {
        Set<Edge> edges = newHashSet();
        for (int i = 0; i < graph.V(); ++i) {
            for (Edge edge : graph.getEdges(i)) {
                edges.add(edge);
            }
        }
        return edges;
    }
}
