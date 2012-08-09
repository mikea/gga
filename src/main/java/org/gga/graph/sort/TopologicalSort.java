package org.gga.graph.sort;

import org.gga.graph.Edge;
import org.gga.graph.Graph;
import org.gga.graph.exceptions.NotADagException;
import org.gga.graph.search.dfs.AbstractDfsVisitor;
import org.gga.graph.search.dfs.DepthFirstSearch;

/**
 * @author mike.aizatsky
 */
public class TopologicalSort {
    public static int[] sort(final Graph g) throws NotADagException {
        final int[] result = new int[g.V()];

        DepthFirstSearch.depthFirstSearch(g, new AbstractDfsVisitor() {
            int pos = g.V() - 1;

            @Override
            public void backEdge(Edge e, Graph graph) {
                throw new NotADagException();
            }

            @Override
            public void finishVertex(int v, Graph graph) {
                result[pos--] = v;
            }
        });
        return result;
    }
}
