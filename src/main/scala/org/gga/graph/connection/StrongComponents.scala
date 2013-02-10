package org.gga.graph.connection

import com.google.common.base.Preconditions
import org.gga.graph.Graph
import org.gga.graph.search.dfs.AbstractDfsVisitor
import collection.mutable

/**
 * @author mike
 */
object StrongComponents {
  def strongComponents(graph: Graph, componentMap: Array[Int], rootMap: Array[Int]): Int = {
    Preconditions.checkArgument(graph.isDirected)
    val visitor = new StrongComponents.TarjanSccVisitor(rootMap, componentMap, new Array[Int](graph.V))
    graph.getDfs.depthFirstSearch(visitor)
    visitor.currentComponent
  }

  private class TarjanSccVisitor(aRootMap: Array[Int], aComponentMap: Array[Int], aDiscoverTime: Array[Int]) extends AbstractDfsVisitor {
    private val rootMap = aRootMap
    private val componentMap = aComponentMap
    private val discoverTime = aDiscoverTime

    private var dfsTime: Int = 0
    var currentComponent: Int = 0
    private final val stack: mutable.Stack[Int] = new mutable.Stack

    override def discoverVertex(v: Int, graph: Graph) {
      rootMap(v) = v
      componentMap(v) = Integer.MAX_VALUE
      discoverTime(v) = ({
        dfsTime += 1; dfsTime
      })
      stack.push(v)
    }

    override def finishVertex(v: Int, graph: Graph) {
      for (e <- graph.edges(v)) {
        val w: Int = e.other(v)
        if (componentMap(w) == Integer.MAX_VALUE) {
          rootMap(v) = minDiscoverTime(rootMap(v), rootMap(w))
        }
      }
      if (rootMap(v) == v) {
        var w: Int = 0
        do {
          w = stack.pop()
          componentMap(w) = currentComponent
          rootMap(w) = v
        } while (w != v)
        currentComponent += 1
      }
    }

    private def minDiscoverTime(v: Int, w: Int): Int = {
      if (discoverTime(v) < discoverTime(w)) v else w
    }
  }
}