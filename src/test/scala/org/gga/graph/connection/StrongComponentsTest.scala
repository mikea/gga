package org.gga.graph.connection

import junit.framework.{Assert, TestCase}
import org.gga.graph.MutableGraph
import org.gga.graph.impl.SparseGraphImpl
import org.gga.graph.util.ArrayUtil

/**
 * @author mike
 */
class StrongComponentsTest extends TestCase {
  def testStrongComponents {
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
    val componentMap: Array[Int] = new Array[Int](13)
    val rootMap: Array[Int] = new Array[Int](13)
    StrongComponents.strongComponents(graph, componentMap, rootMap)
    Assert.assertEquals("[2, 0, 2, 2, 2, 2, 2, 3, 3, 1, 1, 1, 1]", ArrayUtil.arrayToString(componentMap))
    Assert.assertEquals("[0, 1, 0, 0, 0, 0, 0, 7, 7, 11, 11, 11, 11]", ArrayUtil.arrayToString(rootMap))
  }
}