package org.gga.graph.search.dijkstra

import org.gga.graph.search.bfs.BfsVisitor
import org.gga.graph.{Graph, Edge}

/**
 * @author mike.aizatsky@gmail.com
 */
trait DijkstraVisitor extends BfsVisitor {
  def edgeRelaxed(e: Edge, g: Graph)
  def edgeNotRelaxed(e: Edge, g: Graph)

}
