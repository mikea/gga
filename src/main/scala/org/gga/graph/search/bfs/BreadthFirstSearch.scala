package org.gga.graph.search.bfs

import org.gga.graph.Graph
import org.gga.graph.util.ColorValue
import org.gga.graph.util.IntQueue

/**
 * @author mike
 */
object BreadthFirstSearch {
  def breadthFirstSearch(g: Graph, visitor: BfsVisitor, colorMap: Array[Short], firstVertex: Int) {
    g.vertices.map{visitor.initializeVertex(_, g)}
    bfs(g, visitor, colorMap, firstVertex)
  }

  def breadthFirstSearch(g: Graph, visitor: BfsVisitor, firstVertex: Int) {
    val colorMap: Array[Short] = Array.fill(g.V){ColorValue.WHITE}
    breadthFirstSearch(g, visitor, colorMap, firstVertex)
  }

  private def bfs(g: Graph, visitor: BfsVisitor, colorMap: Array[Short], firstVertex: Int) {
    val q: IntQueue = new IntQueue
    colorMap(firstVertex) = ColorValue.GREY
    visitor.discoverVertex(firstVertex, g)
    q.push(firstVertex)
    while (!q.isEmpty) {
      val v: Int = q.pop
      visitor.examineVertex(v, g)
      for (e <- g.getEdges(v)) {
        visitor.examineEdge(e, g)
        val w: Int = e.w
        val c: Short = colorMap(w)
        if (c == ColorValue.WHITE) {
          visitor.treeEdge(e, g)
          colorMap(w) = ColorValue.GREY
          visitor.discoverVertex(w, g)
          q.push(w)
        }
        else {
          visitor.nonTreeEdge(e, g)
          if (c == ColorValue.GREY) {
            visitor.greyTarget(e, g)
          }
          else {
            visitor.blackTarget(e, g)
          }
        }
      }
      colorMap(v) = ColorValue.BLACK
      visitor.finishVertex(v, g)
    }
  }
}