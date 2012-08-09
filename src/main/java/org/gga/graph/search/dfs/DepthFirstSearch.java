package org.gga.graph.search.dfs;

import org.gga.graph.Edge;
import org.gga.graph.Graph;
import org.gga.graph.util.ColorValue;

/**
 * @author mike
 */
public class DepthFirstSearch {
    public static void depthFirstSearch(Graph graph, DfsVisitor visitor, short[] colorMap) {
        depthFirstSearch(graph, visitor, colorMap, 0);
    }

    public static void depthFirstSearch(
            Graph graph,
            DfsVisitor visitor,
            short[] colorMap,
            int firstVertex) {
        for (int v = 0; v < graph.V(); v++) {
            colorMap[v] = ColorValue.WHITE;
            visitor.initializeVertex(v, graph);
        }

        visitor.startVertex(firstVertex, graph);
        _dfs(graph, visitor, colorMap, firstVertex);

        for (int v = 0; v < graph.V(); v++) {
            if (colorMap[v] == ColorValue.WHITE) {
                visitor.startVertex(v, graph);
                _dfs(graph, visitor, colorMap, v);
            }
        }
    }

    private static void _dfs(Graph graph, DfsVisitor visitor, short[] colorMap, int v) {
        colorMap[v] = ColorValue.GREY;

        visitor.discoverVertex(v, graph);

        for (Edge e : graph.getEdges(v)) {
            int w = e.other(v);

            visitor.examineEdge(e, graph);

            if (colorMap[w] == ColorValue.WHITE) {
                visitor.treeEdge(e, graph);
                _dfs(graph, visitor, colorMap, w);
            } else if (colorMap[w] == ColorValue.GREY) {
                visitor.backEdge(e, graph);
            } else {
                visitor.forwardOrCrossEdge(e, graph);
            }
        }

        visitor.finishVertex(v, graph);

        colorMap[v] = ColorValue.BLACK;
    }

    public static void depthFirstSearch(Graph graph, DfsVisitor visitor) {
        depthFirstSearch(graph, visitor, new short[graph.V()]);
    }
}
