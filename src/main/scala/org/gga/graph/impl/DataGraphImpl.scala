package org.gga.graph.impl

import org.gga.graph.{Graph, Edge, MutableGraph}
import org.gga.graph.maps.BiVertexMap
import org.gga.graph.maps.DataGraph
import org.gga.graph.maps.EdgeMap
import javax.annotation.Nullable

/**
 * @author mike
 */
class DataGraphImpl[N, E](aSize: Int, anIsDirected: Boolean) extends DataGraph[N, E] {
  private final val graph: MutableGraph = new SparseGraphImpl(aSize, anIsDirected)
  private final val vertexMap: BiVertexMap[N] = new BiVertexMapImpl[N]
  private final val edges: EdgeMap[E] = new EdgeMapImpl[E]

  def V: Int = graph.V

  def isDirected: Boolean = graph.isDirected

  @Nullable def edge(n1: N, n2: N): Option[E] = {
    val v1: Option[Int] = vertexMap.getVertex(n1)
    val v2: Option[Int] = vertexMap.getVertex(n2)

    (v1, v2) match {
      case (Some(vv1), Some(vv2)) => edge(vv1, vv2)
      case _ => None
    }
  }

  def edge(v1: Int, v2: Int): Option[E] = {
    graph.edge(v1, v2).map((e: Edge) => getEdge(e))
  }

  def getEdge(e: Edge): E = edges.get(e).get

  def insert(n1: N, n2: N, e: E): Option[Edge] = {
    val v1: Option[Int] = vertexMap.getVertex(n1)
    val v2: Option[Int] = vertexMap.getVertex(n2)

    (v1, v2) match {
      case (Some(vv1), Some(vv2)) => Some(insert(vv1, vv2, e))
      case _ => None
    }
  }

  def insert(v1: Int, v2: Int, e: E): Edge = {
    val edge: Edge = graph.insert(v1, v2)
    edges.put(edge, e)
    edge
  }

  def remove(edge: Edge) { graph.remove(edge) }

  def getIntGraph: Graph = graph

  def getIndex(data: N): Option[Int] = vertexMap.getVertex(data)

  def getNode(v: Int): N = vertexMap.get(v).get

  def setNode(v: Int, data: N) { vertexMap.put(v, data) }

  override def toString = {
    val result = new StringBuilder("DataGraphImpl{")
    result.append("isDirected=")
    result.append(isDirected)
    result.append(", ")
    result.append("[")
    for (v <- 0 until V) {
      for (edge <- graph.getEdges(v)) {
        if (!isDirected || edge.other(v) >= v) {
          if (result.charAt(result.length - 1) != '[') {
            result.append(", ")
          }
          result.append(getNode(v))
          if (isDirected) {
            result.append("->")
          } else {
            result.append("<->")
          }
          result.append(getNode(edge.other(v)))
          result.append(":")
          result.append(getEdge(edge))
        }
      }
    }
    result.append("]")
    result.append("}")
    result.toString()
  }
}