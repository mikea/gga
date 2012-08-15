package org.gga.graph.sort

import junit.framework.{Assert, TestCase}
import org.gga.graph.MutableGraph
import org.gga.graph.impl.SparseGraphImpl

/**
 * @author mike.aizatsky
 */
class TopologicalSortTest extends TestCase {
  def testSmoke {
    val g: MutableGraph = new SparseGraphImpl(4, true)
    g.insert(2, 1)
    g.insert(2, 3)
    g.insert(1, 0)
    g.insert(3, 0)
    val vertices: Array[Int] = TopologicalSort.sort(g)
    Assert.assertEquals(4, vertices.length)
    Assert.assertEquals(2, vertices(0))
    Assert.assertEquals(3, vertices(1))
    Assert.assertEquals(1, vertices(2))
    Assert.assertEquals(0, vertices(3))
  }
}