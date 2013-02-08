package org.gga.graph.search.dfs

import org.gga.graph.Edge
import org.gga.graph.Graph
import org.gga.graph.util.ColorValue
import org.gga.graph.impl.FilteredGraph

/**
 * @author mike
 */
object DepthFirstSearch {
  def depthFirstSearch(graph: Graph, visitor: DfsVisitor) {
    depthFirstSearch(0, graph, visitor)
  }

  def depthFirstSearch(startVertex: Int, graph: Graph, visitor: DfsVisitor) {
    depthFirstSearch(graph, visitor, new Array[Short](graph.V), startVertex)
  }

  def depthFirstSearch(graph: Graph, visitor: DfsVisitor, colorMap: Array[Short]) {
    depthFirstSearch(graph, visitor, colorMap, 0)
  }

  def depthFirstSearch(graph: Graph, visitor: DfsVisitor, colorMap: Array[Short], firstVertex: Int) {
    for (v <- graph.vertices) {
      colorMap(v) = ColorValue.WHITE
      visitor.initializeVertex(v, graph)
    }
    visitor.startVertex(firstVertex, graph)
    dfsImpl(graph, visitor, colorMap, firstVertex)

    for (v <- graph.vertices) {
      if (colorMap(v) == ColorValue.WHITE) {
        visitor.startVertex(v, graph)
        dfsImpl(graph, visitor, colorMap, v)
      }
    }
  }

  private def dfsImpl(graph: Graph, visitor: DfsVisitor, colorMap: Array[Short], v: Int) {
    colorMap(v) = ColorValue.GREY
    visitor.discoverVertex(v, graph)
    for (e <- graph.getEdges(v)) {
      val w: Int = e.other(v)
      visitor.examineEdge(e, graph)
      if (colorMap(w) == ColorValue.WHITE) {
        visitor.treeEdge(e, graph)
        dfsImpl(graph, visitor, colorMap, w)
      }
      else if (colorMap(w) == ColorValue.GREY) {
        visitor.backEdge(e, graph)
      } else {
        visitor.forwardOrCrossEdge(e, graph)
      }
    }
    visitor.finishVertex(v, graph)
    colorMap(v) = ColorValue.BLACK
  }
}