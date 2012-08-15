package org.gga.graph.search.bfs

import org.gga.graph.Edge
import org.gga.graph.Graph

/**
 * @author mike
 */
trait AbstractBfsVisitor extends BfsVisitor {
  override def initializeVertex(v: Int, g: Graph) {
  }

  override def discoverVertex(v: Int, g: Graph) {
  }

  override def examineVertex(v: Int, g: Graph) {
  }

  override def finishVertex(v: Int, g: Graph) {
  }

  override def examineEdge(e: Edge, g: Graph) {
  }

  override def treeEdge(e: Edge, g: Graph) {
  }

  override def nonTreeEdge(e: Edge, g: Graph) {
  }

  override def grayTarget(e: Edge, g: Graph) {
  }

  override def blackTarget(e: Edge, g: Graph) {
  }
}