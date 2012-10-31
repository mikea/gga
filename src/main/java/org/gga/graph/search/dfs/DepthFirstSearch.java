package org.gga.graph.search.dfs;

import org.gga.graph.Edge;
import org.gga.graph.Graph;
import org.gga.graph.util.ColorValue;

import java.util.Arrays;

/**
 * @author mike
 *
 * White marks vertices that have yet to be discovered, gray marks a vertex that is discovered but still has vertices
 * adjacent to it that are undiscovered. A black vertex is discovered vertex that is not adjacent to any white vertices.
 *
 */
public final class DepthFirstSearch {
    private DepthFirstSearch() {
    }

    private static class UndirectedSearch {
        private static void undirectedDepthFirstSearchImpl(Graph graph, DfsVisitor visitor, short[] colorMap, short[] edgeColors, int firstVertex) {
            for (int v = 0; v < graph.V(); v++) {
                colorMap[v] = ColorValue.WHITE;
                visitor.initializeVertex(v, graph);
            }

            Arrays.fill(edgeColors, ColorValue.WHITE);

            visitor.startVertex(firstVertex, graph);
            udfsImpl(graph, visitor, colorMap, edgeColors, firstVertex);

            for (int v = 0; v < graph.V(); v++) {
                if (colorMap[v] == ColorValue.WHITE) {
                    visitor.startVertex(v, graph);
                    udfsImpl(graph, visitor, colorMap, edgeColors, v);
                }
            }
        }

        private static void udfsImpl(Graph graph, DfsVisitor visitor, short[] colorMap, short[] edgeColors, int v) {
            colorMap[v] = ColorValue.GRAY;

            visitor.discoverVertex(v, graph);

            for (Edge e : graph.getEdges(v)) {
                int w = e.other(v);

                visitor.examineEdge(e, graph);
                short edgeColor = edgeColors[e.idx()];
                edgeColors[e.idx()] = ColorValue.BLACK;

                if (colorMap[w] == ColorValue.WHITE) {
                    visitor.treeEdge(e, graph);
                    udfsImpl(graph, visitor, colorMap, edgeColors, w);
                } else if (colorMap[w] == ColorValue.GRAY && edgeColor == ColorValue.WHITE) {
                    visitor.backEdge(e, graph);
                }
            }

            visitor.finishVertex(v, graph);

            colorMap[v] = ColorValue.BLACK;
        }
    }

    private static class DirectedSearch {
        private static void depthFirstSearchImpl(
                Graph graph,
                DfsVisitor visitor,
                short[] colorMap,
                int firstVertex) {
            for (int v = 0; v < graph.V(); v++) {
                colorMap[v] = ColorValue.WHITE;
                visitor.initializeVertex(v, graph);
            }

            visitor.startVertex(firstVertex, graph);
            dfsImpl(graph, visitor, colorMap, firstVertex);

            for (int v = 0; v < graph.V(); v++) {
                if (colorMap[v] == ColorValue.WHITE) {
                    visitor.startVertex(v, graph);
                    dfsImpl(graph, visitor, colorMap, v);
                }
            }
        }

        private static void dfsImpl(Graph graph, DfsVisitor visitor, short[] colorMap, int v) {
            colorMap[v] = ColorValue.GRAY;

            visitor.discoverVertex(v, graph);

            for (Edge e : graph.getEdges(v)) {
                int w = e.other(v);

                visitor.examineEdge(e, graph);

                if (colorMap[w] == ColorValue.WHITE) {
                    visitor.treeEdge(e, graph);
                    dfsImpl(graph, visitor, colorMap, w);
                } else if (colorMap[w] == ColorValue.GRAY) {
                    visitor.backEdge(e, graph);
                } else {
                    visitor.forwardOrCrossEdge(e, graph);
                }
            }

            visitor.finishVertex(v, graph);

            colorMap[v] = ColorValue.BLACK;
        }
    }

    public static void depthFirstSearch(Graph graph, DfsVisitor visitor) {
        depthFirstSearch(graph, 0, visitor);
    }

    public static void depthFirstSearch(Graph graph, int v, DfsVisitor visitor) {
        if (graph.isDirected()) {
            DirectedSearch.depthFirstSearchImpl(graph, visitor, new short[graph.V()], v);
        } else {
            UndirectedSearch.undirectedDepthFirstSearchImpl(graph, visitor, new short[graph.V()], new short[graph.E()], v);
        }
    }
}
