package org.gga.graph

import org.gga.graph.search.dfs.HasDfs

/**
 * Sedgewick-inspired graph
 *
 * @author mike
 */
trait Graph extends HasDfs {
  /**
   * @return number of vertices in the graph.
   */
  def V: Int

  def vertices: Range = Range(0, V)

  /**
   * @return number of edges in the graph.
   */
  def E: Int

  def isDirected: Boolean

  def edge(v: Int, w: Int): Option[Edge]

  /**
   * Iterate over edges out of the vertex.
   */
  def getEdgeIterator(v: Int): EdgeIterator

  /**
   * Iterate over edges out of the vertex.
   */
  def getEdgesIterator(v: Int): Iterator[Edge]

  /**
   * Iterate over edges out of the vertex.
   */
  def edges(v: Int): Iterable[Edge]


  def allEdges: Set[Edge] = {
    var result: Set[Edge] = Set()

    for (v <- vertices) {
      result ++= edges(v)
    }

    result
  }

  override def toString = {
    val result = new StringBuilder(getClass().getSimpleName());
    result.append('{')
    result.append("V=")
    result.append(V)
    result.append(", isDirected=")
    result.append(isDirected)
    result.append(", [\n")
    for (v <- vertices) {
      for (e <- edges(v)) {
        if (isDirected || !(e.other(v) < v)) {
          result.append("    ")
          result.append(e.v)
          if (isDirected) {
            result.append("->")
          } else {
            result.append("<->")
          }
          result.append(e.w)
          result.append("\n")
        }
      }
    }
    result.append("]}")
    result.toString
  }
}