package org.gga.graph.impl

import org.gga.graph.maps.BiVertexMap
import javax.annotation.Nullable
import scala.collection.mutable

/**
 * @author mike
 */
class BiVertexMapImpl[V >: Null] extends BiVertexMap[V] {
  private var data: mutable.ArraySeq[V] = mutable.ArraySeq.empty
  private final val map: mutable.Map[V, Int] = mutable.Map.empty[V, Int]

  def getVertex(data: V): Option[Int] = map.get(data)

  def put(v: Int, data: V) {
    map.put(data, v)
    if (v >= this.data.length) {
      val newSize: Int = v max (this.data.length + 1) * 2
      this.data = this.data.padTo(newSize, null: V)
    }
    this.data(v) = data
  }

  @Nullable def get(v: Int): Option[V] = {
    if (v >= data.length) {
      return None
    }
    Some(data(v))
  }

  override def toString = map.toString()
}