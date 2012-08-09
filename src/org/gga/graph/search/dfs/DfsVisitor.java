package org.gga.graph.search.dfs;

import org.gga.graph.Edge;
import org.gga.graph.Graph;

/**
 * This is boost-inspired DfsVisitor.
 *
 * @author mike
 */
public interface DfsVisitor {

    /**
     * This is invoked on every vertex of the graph before the start of the graph search.
     */
    void initializeVertex(int v, Graph graph);

    /**
     * This is invoked on the source vertex once before the start of the search.
     */
    void startVertex(int v, Graph graph);

    /**
     * This is invoked when a vertex is encountered for the first time.
     */
    void discoverVertex(int v, Graph graph);

    /**
     * This is invoked on every out-edge of each vertex after it is discovered.
     */
    void examineEdge(Edge e, Graph graph);

    /**
     * This is invoked on each edge as it becomes a member of the edges that form the search tree.
     */
    void treeEdge(Edge e, Graph graph);

    /**
     * This is invoked on the back edges in the graph. For an undirected graph there is some ambiguity between tree
     * edges and back edges since the edge (u,v) and (v,u) are the same edge, but both the treeEdge() and backEdge()
     * functions will be invoked. One way to resolve this ambiguity is to record the tree edges, and then disregard
     * the back-edges that are already marked as tree edges. An easy way to record tree edges is to record predecessors
     * at the tree_edge event point.
     */
    void backEdge(Edge e, Graph graph);

    /**
     * This is invoked on forward or cross edges in the graph. In an undirected graph this method is never called.
     */
    void forwardOrCrossEdge(Edge e, Graph graph);

    /**
     * This is invoked on vertex u after finish_vertex has been called for all the vertices in the DFS-tree rooted at
     * vertex u. If vertex u is a leaf in the DFS-tree, then the finishVertex() function is call on u after all
     * the out-edges of u have been examined.
     */
    void finishVertex(int v, Graph graph);
}
