package org.gga.graph.transform

import org.gga.graph.Edge
import org.gga.graph.Graph
import org.gga.graph.MutableGraph

/**
 * @author mike
 */
object Reverse {
  def reverseGraph(src: Graph, dst: MutableGraph, edgeMap: Array[Int]): Graph = {
    for (v <- src.vertices) {
      for (e <- src.getEdges(v)) {
        val e1: Edge = dst.insert(e.w, v)
        if (edgeMap != null) {
          edgeMap(e1.idx) = e.idx
        }
      }
    }

    dst
  }
}