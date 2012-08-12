package org.gga.graph.impl

import junit.framework.{Assert, TestCase}
import org.gga.graph.{Edge, MutableGraph}

/**
 * @author mike
 */
class SparseGraphImplTest extends TestCase {
  val graph: MutableGraph = new SparseGraphImpl(10, true)

  override def setUp() {
    super.setUp()

    graph.insert(0, 1)
    graph.insert(0, 2)
    graph.insert(0, 3)
  }

  def testDirectedInsert() {

    assertIsSome(graph.edge(0, 1))
    assertIsSome(graph.edge(0, 2))
    assertIsSome(graph.edge(0, 3))
    assertIsNone(graph.edge(1, 0))
    assertIsNone(graph.edge(2, 0))
    assertIsNone(graph.edge(3, 0))
  }

  def testEdgesIterator() {
    var edges: Int = 0

    for (v <- graph.vertices) {
      for (e <- graph.getEdges(v)) {
        edges += 1
      }
    }

    Assert.assertEquals(3, edges)
  }

  def assertIsNone(option: Option[Edge]) {
    Assert.assertTrue(option.isEmpty)
  }

  def assertIsSome(option: Option[Edge]) {
    Assert.assertTrue(option.isDefined)
  }
}