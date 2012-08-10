package org.gga.graph.maps

import javax.annotation.Nullable

/**
 * @author mike
 */
trait VertexMap[V] {
  def put(v: Int, data: V)

  def get(v: Int): Option[V]
}