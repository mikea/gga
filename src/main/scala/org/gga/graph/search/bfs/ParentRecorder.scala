package org.gga.graph.search.bfs

import org.gga.graph.Edge
import org.gga.graph.Graph
import java.util.Arrays
import java.util

/**
 * @author mike
 */
object ParentRecorder {
  def computeParents(g: Graph, firstVertex: Int): Array[Int] = {
    val parents = new Array[Int](g.V)
    BreadthFirstSearch.breadthFirstSearch(g, new ParentRecorder(parents), firstVertex)
    parents
  }
}

class ParentRecorder(aParents: Array[Int]) extends AbstractBfsVisitor {
  private final val parents: Array[Int] = aParents

  util.Arrays.fill(parents, -1)

  override def treeEdge(e: Edge, g: Graph) {
    parents(e.w) = e.v
  }

}