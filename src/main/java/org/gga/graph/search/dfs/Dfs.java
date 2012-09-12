package org.gga.graph.search.dfs;

/**
 * @author mike.aizatsky@gmail.com
 */
public interface Dfs {
    void depthFirstSearch(DfsVisitor visitor);
    void depthFirstSearch(int startVertex, DfsVisitor visitor);
}
