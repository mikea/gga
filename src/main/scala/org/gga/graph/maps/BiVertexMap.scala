package org.gga.graph.maps

/**
 * @author mike
 */
trait BiVertexMap[V] extends VertexMap[V] {
  def getVertex(data: V): Option[Int]
}