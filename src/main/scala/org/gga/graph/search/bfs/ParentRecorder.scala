package org.gga.graph.search.bfs

import org.gga.graph.Edge
import org.gga.graph.Graph
import java.util.Arrays
import java.util

/**
 * @author mike
 */
class ParentRecorder(aParents: Array[Int]) extends AbstractBfsVisitor {
  private final val parents: Array[Int] = aParents

  util.Arrays.fill(parents, -1)

  override def treeEdge(e: Edge, g: Graph) {
    parents(e.w) = e.v
  }

}