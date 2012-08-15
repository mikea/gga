package org.gga.graph.impl

import org.gga.graph.Edge
import org.gga.graph.maps.EdgeMap
import javax.annotation.Nullable
import scala.None
import collection.mutable

/**
 * @author mike
 */
class EdgeMapImpl[E] extends EdgeMap[E] {
  private var data: mutable.ArraySeq[E] = mutable.ArraySeq.empty

  def put(e: Edge, data: E) {
    val idx: Int = e.getIdx
    if (idx >= this.data.length) {
      val newLen: Int = idx max (this.data.length + 1) * 2
      this.data = this.data.padTo(newLen, null.asInstanceOf[E])
    }
    this.data(idx) = data
  }

  @Nullable
  def get(e: Edge): Option[E] = {
    val idx: Int = e.getIdx
    if (idx >= data.length) return None
    Some(data(idx))
  }
}