package org.gga.graph.maps

import org.gga.graph.Edge
import org.gga.graph.Graph
import javax.annotation.Nullable

/**
 * @author mike
 */
trait DataGraph[N, E] {
  def V: Int
  def vertices: Range = Range(0, V)

  def isDirected: Boolean

  def getIndex(data: N): Option[Int]

  def getNode(v: Int): N

  def setNode(v: Int, data: N)

  def edge(n1: N, n2: N): Option[E]

  def edge(v1: Int, v2: Int): Option[E]

  def getEdge(e: Edge): E

  def insert(n1: N, n2: N, e: E): Option[Edge]

  def insert(v1: Int, v2: Int, e: E): Edge

  def remove(edge: Edge)

  def getIntGraph: Graph
}