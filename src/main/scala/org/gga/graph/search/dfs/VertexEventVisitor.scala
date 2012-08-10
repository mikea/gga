package org.gga.graph.search.dfs

import org.gga.graph.Graph
import org.gga.graph.search.dfs.VertexEventVisitor.VertexEvent

/**
 * @author mike
 */
object VertexEventVisitor {
  object VertexEvent extends Enumeration {
    val INITIALIZE_VERTEX = Value
    val START_VERTEX = Value
    val DISCOVER_VERTEX = Value
    val FINISH_VERTEX = Value
  }
}

trait VertexEventVisitor extends AbstractDfsVisitor {
  def onEvent(v: Int, graph: Graph, event: VertexEvent.Value)

  override def initializeVertex(v: Int, graph: Graph) {
    onEvent(v, graph, VertexEvent.INITIALIZE_VERTEX)
  }

  override def startVertex(v: Int, graph: Graph) {
    onEvent(v, graph, VertexEvent.START_VERTEX)
  }

  override def discoverVertex(v: Int, graph: Graph) {
    onEvent(v, graph, VertexEvent.DISCOVER_VERTEX)
  }

  override def finishVertex(v: Int, graph: Graph) {
    onEvent(v, graph, VertexEvent.FINISH_VERTEX)
  }
}