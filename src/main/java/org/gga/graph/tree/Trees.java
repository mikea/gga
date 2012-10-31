package org.gga.graph.tree;

import org.gga.graph.Edge;
import org.gga.graph.Graph;
import org.gga.graph.search.dfs.AbstractDfsVisitor;
import org.gga.graph.search.dfs.HasDfs;

/**
 * @author mike.aizatsky@gmail.com
 */
public final class Trees {
    public static boolean isTree(HasDfs graph) {
        return isTree(graph, 0);
    }

    public static boolean isTree(HasDfs graph, int root) {
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
