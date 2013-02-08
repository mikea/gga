package org.gga.graph.sort

import org.gga.graph.Edge
import org.gga.graph.Graph
import org.gga.graph.exceptions.NotADagException
import org.gga.graph.search.dfs.AbstractDfsVisitor

/**
 * @author mike.aizatsky
 */
object TopologicalSort {
  /**
   * Returns array with sorted vertices numbers.
   */
  def sort(g: Graph): Array[Int] = {
    val result: Array[Int] = new Array[Int](g.V)

    g.getDfs.depthFirstSearch(new AbstractDfsVisitor {
      private var pos: Int = g.V - 1

      override def backEdge(e: Edge, graph: Graph) {
        throw new NotADagException
      }

      override def finishVertex(v: Int, graph: Graph) {
        result(pos) = v
        pos -= 1
      }
    })
    result
  }
}