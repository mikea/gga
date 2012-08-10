package org.gga.graph

import org.gga.graph.search.dfs.HasDfs
import javax.annotation.Nullable

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

  def getEdgeIterator(v: Int): EdgeIterator

  def getEdgesIterator(v: Int): Iterator[Edge]

  def getEdges(v: Int): Iterable[Edge]
}