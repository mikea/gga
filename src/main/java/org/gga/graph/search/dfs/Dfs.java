package org.gga.graph.search.dfs;

import org.gga.graph.Graph;

/**
 * @author mike.aizatsky@gmail.com
 */
public interface Dfs {
    public void depthFirstSearch(DfsVisitor visitor);
}
