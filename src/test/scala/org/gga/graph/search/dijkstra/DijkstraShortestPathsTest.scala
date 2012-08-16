package org.gga.graph.search.dijkstra

import junit.framework.{Assert, TestCase}
import org.gga.graph.{Graph, Edge}
import org.gga.graph.impl.{EdgeMapImpl, SparseGraphImpl}
import collection.mutable
import org.gga.graph.search.dijkstra.DijkstraShortestPathsTest.MyVisitor
import org.gga.graph.util.ArrayUtil

object DijkstraShortestPathsTest {
  class MyVisitor extends DijkstraVisitor {
    val events: mutable.Buffer[String] = mutable.Buffer.empty

    def examineEdge(e: Edge, g: Graph) {
      events += "examineEdge(" + e + ")"
    }

    def examineVertex(v: Int, g: Graph) {
      events += "examineVertex(" + v + ")"
    }

    def blackTarget(e: Edge, g: Graph) {
      Assert.fail("Shouldn't happen")
    }

    def treeEdge(e: Edge, g: Graph) {
      Assert.fail("Shouldn't happen")
    }

    def grayTarget(e: Edge, g: Graph) {
      Assert.fail("Shouldn't happen")
    }

    def edgeNotRelaxed(e: Edge, g: Graph) {
      events += "edgeNotRelaxed(" + e + ")"
    }

    def discoverVertex(v: Int, g: Graph) {
      events += "discoverVertex(" + v + ")"
    }

    def initializeVertex(v: Int, g: Graph) {
      events += "initializeVertex(" + v + ")"
    }

    def nonTreeEdge(e: Edge, g: Graph) {
      Assert.fail("Shouldn't happen")
    }

    def finishVertex(v: Int, g: Graph) {
      events += "finishVertex(" + v + ")"
    }

    def edgeRelaxed(e: Edge, g: Graph) {
      events += "edgeRelaxed(" + e + ")"
    }
  }
}
/**
 * @author mike.aizatsky@gmail.com
 */
class DijkstraShortestPathsTest extends TestCase {
  def testDirectedInsert() {
    val graph = new SparseGraphImpl(8, true)
    val weights = new EdgeMapImpl[Double]


    weights.put(graph.insert(4, 5), 0.35)
    weights.put(graph.insert(5, 4), 0.35)
    weights.put(graph.insert(4, 7), 0.37)
    weights.put(graph.insert(5, 7), 0.28)
    weights.put(graph.insert(7, 5), 0.28)
    weights.put(graph.insert(5, 1), 0.32)
    weights.put(graph.insert(0, 4), 0.38)
    weights.put(graph.insert(0, 2), 0.26)
    weights.put(graph.insert(7, 3), 0.39)
    weights.put(graph.insert(1, 3), 0.29)
    weights.put(graph.insert(2, 7), 0.34)
    weights.put(graph.insert(6, 2), 0.40)
    weights.put(graph.insert(3, 6), 0.52)
    weights.put(graph.insert(6, 0), 0.58)
    weights.put(graph.insert(6, 4), 0.93)

    val visitor: MyVisitor = new DijkstraShortestPathsTest.MyVisitor
    val result: (Array[Double], Array[Int]) = DijkstraShortestPaths.dijkstraShortsPaths(graph, weights, visitor, 0)

    Assert.assertEquals(
      "initializeVertex(0), initializeVertex(1), initializeVertex(2), initializeVertex(3), initializeVertex(4), initializeVertex(5), initializeVertex(6), initializeVertex(7), " +
        "discoverVertex(0), examineVertex(0), examineEdge((0 -> 4)), edgeRelaxed((0 -> 4)), discoverVertex(4), examineEdge((0 -> 2)), edgeRelaxed((0 -> 2)), discoverVertex(2), finishVertex(0), " +
        "examineVertex(4), examineEdge((4 -> 5)), edgeRelaxed((4 -> 5)), discoverVertex(5), examineEdge((4 -> 7)), edgeRelaxed((4 -> 7)), discoverVertex(7), finishVertex(4), " +
        "examineVertex(2), examineEdge((2 -> 7)), edgeRelaxed((2 -> 7)), finishVertex(2), examineVertex(5), examineEdge((5 -> 4)), examineEdge((5 -> 7)), edgeNotRelaxed((5 -> 7)), examineEdge((5 -> 1)), edgeRelaxed((5 -> 1)), discoverVertex(1), finishVertex(5), examineVertex(7), examineEdge((7 -> 5)), examineEdge((7 -> 3)), edgeRelaxed((7 -> 3)), discoverVertex(3), finishVertex(7), examineVertex(1), examineEdge((1 -> 3)), edgeNotRelaxed((1 -> 3)), finishVertex(1), examineVertex(3), examineEdge((3 -> 6)), edgeRelaxed((3 -> 6)), discoverVertex(6), finishVertex(3), examineVertex(6), examineEdge((6 -> 2)), examineEdge((6 -> 0)), examineEdge((6 -> 4)), finishVertex(6)",
      visitor.events.mkString(", "))

    Assert.assertEquals("[0.0, 1.05, 0.26, 0.9900000000000001, 0.38, 0.73, 1.5100000000000002, 0.6000000000000001]", ArrayUtil.arrayToString(result._1))
    Assert.assertEquals("[-1, 5, 0, 7, 0, 4, 3, 2]", ArrayUtil.arrayToString(result._2))

    Assert.assertEquals("[6, -1, 6, 1, 6, 7, 3, 2]", ArrayUtil.arrayToString(DijkstraShortestPaths.dijkstraShortsPaths(graph, weights, visitor, 1)._2))
    Assert.assertEquals("[6, 5, -1, 7, 5, 7, 3, 2]", ArrayUtil.arrayToString(DijkstraShortestPaths.dijkstraShortsPaths(graph, weights, visitor, 2)._2))
    Assert.assertEquals("[6, 5, 6, -1, 6, 7, 3, 2]", ArrayUtil.arrayToString(DijkstraShortestPaths.dijkstraShortsPaths(graph, weights, visitor, 3)._2))
    Assert.assertEquals("[6, 5, 6, 7, -1, 4, 3, 4]", ArrayUtil.arrayToString(DijkstraShortestPaths.dijkstraShortsPaths(graph, weights, visitor, 4)._2))
    Assert.assertEquals("[6, 5, 6, 1, 5, -1, 3, 5]", ArrayUtil.arrayToString(DijkstraShortestPaths.dijkstraShortsPaths(graph, weights, visitor, 5)._2))
    Assert.assertEquals("[6, 5, 6, 7, 6, 7, -1, 2]", ArrayUtil.arrayToString(DijkstraShortestPaths.dijkstraShortsPaths(graph, weights, visitor, 6)._2))
    Assert.assertEquals("[6, 5, 6, 7, 5, 7, 3, -1]", ArrayUtil.arrayToString(DijkstraShortestPaths.dijkstraShortsPaths(graph, weights, visitor, 7)._2))
  }
}
