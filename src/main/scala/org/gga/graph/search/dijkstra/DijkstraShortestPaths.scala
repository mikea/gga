package org.gga.graph.search.dijkstra

import org.gga.graph.{Edge, Graph}
import org.gga.graph.search.bfs.{BfsVisitor, BreadthFirstSearch}
import org.gga.graph.maps.EdgeMap

/**
 * @author mike.aizatsky@gmail.com
 */
object DijkstraShortestPaths {
  // TODO: weights -> relax type
  def dijkstraShortsPaths(g: Graph, weights: EdgeMap[Double], visitor: DijkstraVisitor, start: Int): (Array[Double], Array[Int]) = {
    val shortestDistance: Array[Double] = Array.fill(g.V){Double.PositiveInfinity} // TODO: EdgeMap
    val parents: Array[Int] = Array.fill(g.V){-1}

    shortestDistance(start) = 0

    // TODO: use min-queue
    BreadthFirstSearch.breadthFirstSearch(g, new BfsVisitor() {
      def examineEdge(e: Edge, g: Graph) {
        val weight: Option[Double] = weights.get(e)
        weight match {
          case Some(w) => if (w < 0.0) { throw new IllegalArgumentException("Negative weight for edge: " + e) }
          case None => throw new IllegalArgumentException("Edge wieght not set: " + e)

        }
        visitor.examineEdge(e, g)
      }

      def treeEdge(e: Edge, g: Graph) {
        val decreased: Boolean = relax(e, weights, shortestDistance, parents)

        if (decreased) {
          visitor.edgeRelaxed(e, g)
        } else {
          visitor.edgeNotRelaxed(e, g)
        }
      }

      def grayTarget(e: Edge, g: Graph) {
        val decreased: Boolean = relax(e, weights, shortestDistance, parents)

        if (decreased) {
          visitor.edgeRelaxed(e, g)
        } else {
          visitor.edgeNotRelaxed(e, g)
        }
      }

      def examineVertex(v: Int, g: Graph) { visitor.examineVertex(v, g) }
      def discoverVertex(v: Int, g: Graph) { visitor.discoverVertex(v, g) }
      def initializeVertex(v: Int, g: Graph) { visitor.initializeVertex(v, g) }
      def finishVertex(v: Int, g: Graph) { visitor.finishVertex(v, g) }

      def blackTarget(e: Edge, g: Graph) {}
      def nonTreeEdge(e: Edge, g: Graph) {}
    }, start)

    (shortestDistance, parents)
  }

  def relax(edge: Edge, weights: EdgeMap[Double], distances: Array[Double], parents: Array[Int]): Boolean = {
    val dV = distances(edge.v)
    val dW = distances(edge.w)
    val w = weights.get(edge).get

    if (dV + w < dW) {
      parents(edge.w) = edge.v
      distances(edge.w) = dV + w
      true
    } else {
      false
    }
  }
}
