package org.gga.graph.search.dfs

import org.gga.graph.Edge
import org.gga.graph.Graph

/**
 * @author mike
 */
trait AbstractDfsVisitor extends DfsVisitor {
  override def initializeVertex(v: Int, graph: Graph) {
  }

  override def startVertex(v: Int, graph: Graph) {
  }

  override def discoverVertex(v: Int, graph: Graph) {
  }

  override def examineEdge(e: Edge, graph: Graph) {
  }

  override def treeEdge(e: Edge, graph: Graph) {
  }

  override def backEdge(e: Edge, graph: Graph) {
  }

  override def forwardOrCrossEdge(e: Edge, graph: Graph) {
  }

  override def finishVertex(v: Int, graph: Graph) {
  }
}