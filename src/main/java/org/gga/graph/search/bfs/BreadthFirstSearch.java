package org.gga.graph.search.bfs;

import org.gga.graph.Edge;
import org.gga.graph.Graph;
import org.gga.graph.util.ColorValue;
import org.gga.graph.util.IntQueue;

import java.util.Arrays;

/**
 * @author mike
 */
public class BreadthFirstSearch {
    public static void breadthFirstSearch(Graph g, BfsVisitor visitor, short[] colorMap, int firstVertex) {
        for (int i = 0; i < g.V(); i++) {
            visitor.initializeVertex(i, g);
        }

        bfs(g, visitor, colorMap, firstVertex);
    }

    public static void breadthFirstSearch(Graph g, BfsVisitor visitor, int firstVertex) {
        short[] colorMap = new short[g.V()];
        Arrays.fill(colorMap, ColorValue.WHITE);
        breadthFirstSearch(g, visitor, colorMap, firstVertex);
    }

    private static void bfs(Graph g, BfsVisitor visitor, short[] colorMap, int firstVertex) {
        IntQueue q = new IntQueue();

        colorMap[firstVertex] = ColorValue.GRAY;
        visitor.discoverVertex(firstVertex, g);
        q.push(firstVertex);

        while (!q.isEmpty()) {
            int v = q.pop();
            visitor.examineVertex(v, g);

            for (Edge e : g.getEdges(v)) {
                visitor.examineEdge(e, g);

                int w = e.w();
                short c = colorMap[w];

                if (c == ColorValue.WHITE) {
                    visitor.treeEdge(e, g);
                    colorMap[w] = ColorValue.GRAY;
                    visitor.discoverVertex(w, g);
                    q.push(w);
                } else {
                    visitor.nonTreeEdge(e, g);

                    if (c == ColorValue.GRAY) {
                        visitor.greyTarget(e, g);
                    } else {
                        visitor.blackTarget(e, g);
                    }
                }
            }

            colorMap[v] = ColorValue.BLACK;
            visitor.finishVertex(v, g);
        }
    }
}
