package org.gga.graph.tree;

import org.gga.graph.Edge;
import org.gga.graph.Graph;
import org.gga.graph.search.dfs.AbstractDfsVisitor;

/**
 * @author mike.aizatsky@gmail.com
 */
public class Trees {
    public static boolean isTree(Graph graph, int root) {
        final boolean[] result = {true};
        graph.getDfs().depthFirstSearch(root, new AbstractDfsVisitor() {
            @Override
            public void backEdge(Edge e, Graph graph) {
                result[0] = false;
            }

            @Override
            public void forwardOrCrossEdge(Edge e, Graph graph) {
                result[0] = false;
            }
        });
        return result[0];
    }
}
