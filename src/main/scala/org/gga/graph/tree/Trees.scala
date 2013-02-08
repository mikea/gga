package org.gga.graph.tree

import org.gga.graph.Edge
import org.gga.graph.Graph
import org.gga.graph.search.dfs.AbstractDfsVisitor

/**
 * @author mike.aizatsky@gmail.com
 */
object Trees {
  def isTree(graph: Graph, root: Int): Boolean = {
    var result: Boolean = false
    graph.getDfs.depthFirstSearch(root, new AbstractDfsVisitor {
      override def backEdge(e: Edge, graph: Graph) {
        result = false
      }

      override def forwardOrCrossEdge(e: Edge, graph: Graph) {
        result = false
      }
    })
    result
  }
}