package org.gga.graph.transform

import org.gga.graph.Graph
import org.gga.graph.MutableGraph
import org.gga.graph.impl.DataGraphImpl
import org.gga.graph.impl.SparseGraphImpl
import org.gga.graph.maps.DataGraph
import com.google.common.base.Preconditions.checkState
import collection.mutable

/**
 * @author mike
 */
object Morph {
  def morphGraph(g: Graph, map: (Int)=>Int): Graph = {
    val vertexMap: Array[Int] = new Array[Int](g.V)
    var newSize: Int = -1

    for (v <- g.vertices) {
      val t = map(v)
      vertexMap(v) = t
      newSize = newSize max t
    }

    newSize += 1
    checkState(newSize > 0)

    val edges: scala.collection.mutable.Set[(Int, Int)] = scala.collection.mutable.Set.empty
    val result: MutableGraph = new SparseGraphImpl(newSize, g.isDirected)

    for (v <- g.vertices) {
      for (e <- g.edges(v)) {
        val v1: Int = vertexMap(v)
        val w1: Int = vertexMap(e.w)

        val p = (v1, w1)
        if (!edges.contains(p)) {
          result.insert(v1, w1)
          edges.add(p)
        }
      }
    }

    result
  }

  def isomorph[N >: AnyRef, E](g: DataGraph[N, E], map: (Int) => Int): DataGraph[N, E] = {
    val vertexMap: Array[Int] = new Array[Int](g.V)
    var newSize: Int = -1

    for (v <- g.vertices) {
      val t: Int = map(v)
      vertexMap(v) = t
      newSize = newSize max t
    }

    newSize += 1
    checkState(newSize > 0 && newSize == g.V)

    val result: DataGraph[N, E] = new DataGraphImpl[N, E](newSize, g.isDirected)

    for (v <- g.vertices) {
      result.setNode(vertexMap(v), g.node(v))
    }

    for (v <- g.vertices) {
      for (e <- g.intGraph.edges(v)) {
        val v1: Int = vertexMap(v)
        val w1: Int = vertexMap(e.w)
        result.insert(v1, w1, g.edge(v, e.w).get)
      }
    }

    result
  }

  def morph[N, E, N1, E1](g: DataGraph[N, E], nodeMap: (N) => (N1), verticesMap: (E) => (E1)): DataGraph[N1, E1] = {
    val nodesDataMap: mutable.ArraySeq[N1] = new mutable.ArraySeq(g.V)

    for (v <- g.vertices) {
      nodesDataMap(v) = nodeMap(g.node(v))
    }

    val newEdges: mutable.Map[N1, mutable.Map[N1, mutable.Buffer[E]]] = mutable.Map.empty
    for (v <- g.vertices) {
      val n1: N1 = nodesDataMap(v)

      val edges = newEdges.getOrElseUpdate(n1, {mutable.Map.empty})

      for (e <- g.intGraph.edges(v)) {
        val n2: N1 = nodesDataMap(e.w)
        var edgesList = edges.getOrElseUpdate(n2, {mutable.Buffer.empty})
        edgesList += g.edge(e)
      }
    }


    // todo: finish
/*
    val newNodes: Array[N1] = newEdges.keySet.toArray
    val newSize: Int = newEdges.size
    val result: DataGraph[N1, E1] = new DataGraphImpl[N1, E1](newSize, g.isDirected)

    {
      var v: Int = 0
      while (v < newSize) {
        {
          result.setNode(v, newNodes(v).asInstanceOf[N1])
        }
        ({
          v += 1; v
        })
      }
    }
    for (n1 <- newEdges.keySet) {
      val edges: Map[N1, List[E]] = newEdges.get(n1)
      for (n2 <- edges.keySet) {
        val oldEdges: List[E] = edges.get(n2)
        val newEdge: E1 = verticesMap.fun(oldEdges)
        result.insert(n1, n2, newEdge)
      }
    }
    return result
*/
    throw new UnsupportedOperationException
  }
}