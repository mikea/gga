package org.gga.graph.impl

import org.gga.graph.{Graph, Edge, MutableGraph}
import org.gga.graph.maps.BiVertexMap
import org.gga.graph.maps.DataGraph
import org.gga.graph.maps.EdgeMap
import javax.annotation.Nullable


object DataGraphImpl {

  class Builder[N, E](isDirected: Boolean) {

  }

}

/**
 * @author mike
 */
class DataGraphImpl[N >: Null, E](aSize: Int, anIsDirected: Boolean) extends DataGraph[N, E] {
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
    graph.edge(v1, v2).map((e: Edge) => edge(e))
  }

  def edge(e: Edge): E = edges.get(e).get

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

  def remove(edge: Edge) {
    graph.remove(edge)
  }

  def intGraph: Graph = graph

  def getIndex(data: N): Option[Int] = vertexMap.getVertex(data)

  def node(v: Int): N = vertexMap.get(v).get

  def setNode(v: Int, data: N) {
    vertexMap.put(v, data)
  }

  def nodes: Seq[N] = vertices.map(node(_))

  override def toString = {
    val result = new StringBuilder("DataGraphImpl{")
    result.append("isDirected=")
    result.append(isDirected)

    // Nodes
    result.append(", ")
    result.append(nodes.map(n => n.toString).sorted.mkString("[", ", ", "]"))
    result.append(", ")

    {
      // Edges
      var edges: Vector[String] = Vector()
      for (v <- vertices) {
        for (edge <- graph.edges(v)) {
          if (isDirected || !(edge.other(v) < v)) {
            val edgeString = new StringBuilder()
            edgeString.append(node(v))
            if (isDirected) {
              edgeString.append("->")
            } else {
              edgeString.append("<->")
            }
            edgeString.append(node(edge.other(v)))
            edgeString.append(":")
            edgeString.append(this.edge(edge))
            edges :+= edgeString.toString()
          }
        }
      }

      edges = edges.sorted
      result.append("[\n")
      for (edge <- edges) {
        result.append("    ")
        result.append(edge)
        result.append("\n")
      }
      result.append("]")
    }

  result.append("}")
  result.toString()
}

}