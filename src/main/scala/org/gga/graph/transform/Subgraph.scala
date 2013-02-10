package org.gga.graph.transform

import org.gga.graph.Graph
import org.gga.graph.MutableGraph
import org.gga.graph.impl.DataGraphImpl
import org.gga.graph.impl.SparseGraphImpl
import org.gga.graph.maps.DataGraph

/**
 * @author mike
 */
object Subgraph {
  def subgraph(g: Graph, verticesToInclude: Array[Boolean]): (Graph, Array[Int]) = {
    var newSize: Int = 0
    val verticesMap: Array[Int] = Array.fill(g.V) {-1}

    for (i <- g.vertices) {
      if (verticesToInclude(i)) {
        verticesMap(i) = newSize
        newSize += 1
      }
    }

    val result: MutableGraph = new SparseGraphImpl(newSize, g.isDirected)

    for (v <- g.vertices) {
      if (verticesToInclude(v)) {
        for (e <- g.edges(v)) {
          if (verticesToInclude(e.w)) {
            result.insert(verticesMap(e.v), verticesMap(e.w))
          }
        }
      }
    }

    (result, verticesMap)
  }

  def subgraph(g: Graph, memberFunction: (Int)=>Boolean): (Graph, Array[Int]) = {
    subgraph(g, g.vertices.map(memberFunction))
  }

  def subgraph[N >: AnyRef, E](g: DataGraph[N, E], memberFunction: (N)=>Boolean): (DataGraph[N, E], Array[Int]) = {
    var newSize: Int = 0
    val verticesMap: Array[Int] = Array.fill(g.V)(-1)

    for (v <- g.vertices) {
      if (memberFunction(g.node(v))) {
        verticesMap(v) = newSize
        newSize += 1
      }
    }
    val result: DataGraph[N, E] = new DataGraphImpl[N, E](newSize, g.isDirected)

    for (v <- g.vertices) {
      if (verticesMap(v) >= 0) {
        result.setNode(verticesMap(v), g.node(v))
      }
    }
    for (v <- g.vertices) {
      if (verticesMap(v) >= 0) {
        for (e <- g.intGraph.edges(v)) {
          if (verticesMap(e.w) >= 0) {
            result.insert(
                verticesMap(e.v),
                verticesMap(e.w),
                g.edge(e.v, e.w).get)
          }
        }
      }
    }

    (result, verticesMap)
  }
}