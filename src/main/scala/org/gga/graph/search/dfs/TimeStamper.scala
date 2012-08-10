package org.gga.graph.search.dfs

import org.gga.graph.search.dfs.VertexEventVisitor.VertexEvent
import org.gga.graph.Graph

/**
 * @author mike
 */
class TimeStamper(aTimeStamps: Array[Int], anEvent: VertexEvent.Value) extends VertexEventVisitor {
  private var time: Int = 0
  private final val timeStamps: Array[Int] = aTimeStamps
  private final val event: VertexEvent.Value = anEvent

  def onEvent(v: Int, graph: Graph, evt: VertexEvent.Value) {
    if (evt == event) {
      timeStamps(v) = time
      time += 1
    }
  }

}