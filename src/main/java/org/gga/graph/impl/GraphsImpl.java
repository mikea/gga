package org.gga.graph.impl;

import org.gga.graph.Edge;
import org.gga.graph.Graph;

/**
 * @author mike.aizatsky@gmail.com
 */
public final class GraphsImpl {
    public static String toString(Graph g) {
        StringBuilder result = new StringBuilder(g.getClass().getSimpleName());
        result.append('{');
        result.append("V=");
        result.append(g.V());
        result.append(", isDirected=");
        result.append(g.isDirected());
        result.append(", [\n");
        for (int i = 0; i < g.V(); ++i) {
            for (Edge edge : g.getEdges(i)) {
                if (!g.isDirected() && edge.other(i) < i) continue;
                result.append("    ");
                result.append(edge.v());
                if (g.isDirected()) {
                    result.append("->");
                } else {
                    result.append("<->");
                }
                result.append(edge.w());
                result.append("\n");
            }
        }
        result.append("]}");

        return result.toString();
    }
}
