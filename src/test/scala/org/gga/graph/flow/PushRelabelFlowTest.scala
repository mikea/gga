package org.gga.graph.flow

import junit.framework.TestCase
import org.gga.graph.MutableGraph
import org.gga.graph.impl.SparseGraphImpl

/**
 * @author mike
 */
class PushRelabelFlowTest extends TestCase {
  def test1 {
    val g: MutableGraph = new SparseGraphImpl(6, true)
    g.insert(0, 1)
    g.insert(0, 2)
    g.insert(0, 3)
    g.insert(1, 3)
    g.insert(1, 4)
    g.insert(2, 1)
    g.insert(2, 5)
    g.insert(3, 4)
    g.insert(3, 5)
    g.insert(4, 5)
    val cap: Array[Int] = Array[Int](2, 3, 2, 1, 1, 1, 2, 2, 3, 2)
  }
}