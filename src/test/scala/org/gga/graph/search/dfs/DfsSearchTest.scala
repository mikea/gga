package org.gga.graph.search.dfs

import junit.framework.{Assert, TestCase}
import org.gga.graph.Edge
import org.gga.graph.Graph
import org.gga.graph.MutableGraph
import org.gga.graph.impl.SparseGraphImpl
import org.gga.graph.util.ArrayUtil
import java.util.ArrayList
import java.util.List
import org.gga.graph.search.dfs.VertexEventVisitor.VertexEvent

/**
 * @author mike
 */
object DfsSearchTest {

  private class MyDfsVisitor extends DfsVisitor {
    private var events: List[String] = new ArrayList[String]

    def initializeVertex(v: Int, graph: Graph) {
      events.add("initializeVertex(" + v + ")")
    }

    def startVertex(v: Int, graph: Graph) {
      events.add("startVertex(" + v + ")")
    }

    def discoverVertex(v: Int, graph: Graph) {
      events.add("discoverVertex(" + v + ")")
    }

    def examineEdge(edge: Edge, graph: Graph) {
      events.add("examineEdge(" + edge.v + "," + edge.w + ")")
    }

    def treeEdge(edge: Edge, graph: Graph) {
      events.add("treeEdge(" + edge.v + "," + edge.w + ")")
    }

    def backEdge(edge: Edge, graph: Graph) {
      events.add("backEdge(" + edge.v + "," + edge.w + ")")
    }

    def forwardOrCrossEdge(edge: Edge, graph: Graph) {
      events.add("forwardOrCrossEdge(" + edge.v + "," + edge.w + ")")
    }

    def finishVertex(v: Int, graph: Graph) {
      events.add("finishVertex(" + v + ")")
    }

    def getEvents = events
  }

}

class DfsSearchTest extends TestCase {
  def testUnidirectedEventsOrder {
    val graph: MutableGraph = new SparseGraphImpl(8, false)
    graph.insert(0, 2)
    graph.insert(0, 5)
    graph.insert(0, 7)
    graph.insert(1, 7)
    graph.insert(2, 6)
    graph.insert(3, 4)
    graph.insert(3, 5)
    graph.insert(4, 5)
    graph.insert(4, 6)
    val visitor: DfsSearchTest.MyDfsVisitor = new DfsSearchTest.MyDfsVisitor
    DepthFirstSearch.depthFirstSearch(graph, visitor, new Array[Short](graph.V))
    Assert.assertEquals("[" + "initializeVertex(0), " + "initializeVertex(1), " + "initializeVertex(2), " + "initializeVertex(3), " + "initializeVertex(4), " + "initializeVertex(5), " + "initializeVertex(6), " + "initializeVertex(7), " + "startVertex(0), " + "discoverVertex(0), " + "examineEdge(0,2), " + "treeEdge(0,2), " + "discoverVertex(2), " + "examineEdge(0,2), " + "backEdge(0,2), " + "examineEdge(2,6), " + "treeEdge(2,6), " + "discoverVertex(6), " + "examineEdge(2,6), " + "backEdge(2,6), " + "examineEdge(4,6), " + "treeEdge(4,6), " + "discoverVertex(4), " + "examineEdge(3,4), " + "treeEdge(3,4), " + "discoverVertex(3), " + "examineEdge(3,4), " + "backEdge(3,4), " + "examineEdge(3,5), " + "treeEdge(3,5), " + "discoverVertex(5), " + "examineEdge(0,5), " + "backEdge(0,5), " + "examineEdge(3,5), " + "backEdge(3,5), " + "examineEdge(4,5), " + "backEdge(4,5), " + "finishVertex(5), " + "finishVertex(3), " + "examineEdge(4,5), " + "forwardOrCrossEdge(4,5), " + "examineEdge(4,6), " + "backEdge(4,6), " + "finishVertex(4), finishVertex(6), finishVertex(2), examineEdge(0,5), forwardOrCrossEdge(0,5), examineEdge(0,7), treeEdge(0,7), discoverVertex(7), examineEdge(0,7), backEdge(0,7), examineEdge(1,7), treeEdge(1,7), discoverVertex(1), examineEdge(1,7), backEdge(1,7), finishVertex(1), finishVertex(7), finishVertex(0)]",
      visitor.getEvents.toString)
  }

  def testUnidirectedTimeStamper {
    val graph: MutableGraph = new SparseGraphImpl(8, false)
    graph.insert(0, 2)
    graph.insert(0, 5)
    graph.insert(0, 7)
    graph.insert(1, 7)
    graph.insert(2, 6)
    graph.insert(3, 4)
    graph.insert(3, 5)
    graph.insert(4, 5)
    graph.insert(4, 6)
    val stamps: Array[Int] = new Array[Int](8)
    val forwardStamper: TimeStamper = new TimeStamper(stamps, VertexEvent.DISCOVER_VERTEX)
    DepthFirstSearch.depthFirstSearch(graph, forwardStamper, new Array[Short](8))
    Assert.assertEquals("[0, 7, 1, 4, 3, 5, 2, 6]", ArrayUtil.arrayToString(stamps))
  }

  def testDirectedSearch {
    val graph: MutableGraph = new SparseGraphImpl(13, true)
    graph.insert(0, 1)
    graph.insert(0, 5)
    graph.insert(0, 6)
    graph.insert(2, 0)
    graph.insert(3, 2)
    graph.insert(4, 2)
    graph.insert(2, 3)
    graph.insert(4, 3)
    graph.insert(5, 4)
    graph.insert(6, 4)
    graph.insert(3, 5)
    graph.insert(7, 6)
    graph.insert(8, 7)
    graph.insert(7, 8)
    graph.insert(6, 9)
    graph.insert(8, 9)
    graph.insert(12, 9)
    graph.insert(9, 10)
    graph.insert(4, 11)
    graph.insert(9, 11)
    graph.insert(10, 12)
    graph.insert(11, 12)
    val stamps: Array[Int] = new Array[Int](graph.V)
    val forwardStamper: TimeStamper = new TimeStamper(stamps, VertexEventVisitor.VertexEvent.DISCOVER_VERTEX)
    val colorMap: Array[Short] = new Array[Short](graph.V)
    DepthFirstSearch.depthFirstSearch(graph, forwardStamper, colorMap)
    Assert.assertEquals("[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]", ArrayUtil.arrayToString(colorMap))
    Assert.assertEquals("[0, 1, 4, 5, 3, 2, 10, 11, 12, 8, 9, 6, 7]", ArrayUtil.arrayToString(stamps))
  }
}