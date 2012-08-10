package org.gga.graph.maps

import org.gga.graph.Edge
import javax.annotation.Nullable

/**
 * @author mike
 */
trait EdgeMap[E] {
  def put(e: Edge, data: E)

  def get(e: Edge): Option[E]
}