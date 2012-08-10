package org.gga.graph.search.bfs

import org.gga.graph.Edge
import org.gga.graph.Graph

/**
 * @author mike
 */
trait BfsVisitor {
  def initializeVertex(v: Int, g: Graph)

  def discoverVertex(v: Int, g: Graph)

  def examineVertex(v: Int, g: Graph)

  def finishVertex(v: Int, g: Graph)

  def examineEdge(e: Edge, g: Graph)

  def treeEdge(e: Edge, g: Graph)

  def nonTreeEdge(e: Edge, g: Graph)

  def greyTarget(e: Edge, g: Graph)

  def blackTarget(e: Edge, g: Graph)
}