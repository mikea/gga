package org.gga.graph.impl

import org.gga.graph.{EdgeIterator, Edge, MutableGraph}
import org.gga.graph.search.dfs.DepthFirstSearch
import org.gga.graph.search.dfs.Dfs
import org.gga.graph.search.dfs.DfsVisitor
import org.gga.graph.impl.SparseGraphImpl.{MyEdgeIterator, EmptyIterator}

/**
 * @author mike
 */
class SparseGraphImpl(v: Int, anIsDigraph: Boolean) extends MutableGraph {
  private final val nVertices: Int = v
  private var nEdges: Int = 0
  private final val isDigraph: Boolean = anIsDigraph
  private final val edges: Array[Array[Edge]] = new Array[Array[Edge]](v)

  def V: Int = nVertices

  def E: Int = nEdges

  def isDirected: Boolean = isDigraph

  def edge(v: Int, w: Int): Option[Edge] = {
    val outEdges: Array[Edge] = edges(v)
    if (outEdges == null) None
    else outEdges.find((e: Edge) => e != null && e.w == w)
  }

  def insert(v: Int, w: Int): Edge = {
    val edge: Edge = new Edge(v, w)
    _insert(edge)
    edge
  }

  private def _insert(e: Edge) {
    e.setIdx(nEdges)
    _insert(e, e.v)
    if (!isDigraph) {
      _insert(e, e.w)
    }
    nEdges += 1
  }

  private def _insert(e: Edge, v: Int) {
    var outEdges: Array[Edge] = edges(v)
    if (outEdges == null) {
      outEdges = new Array[Edge](1)
      edges(v) = outEdges
    }
    {
      var i: Int = 0
      while (i < outEdges.length) {
        {
          val edge: Edge = outEdges(i)
          if (edge == null) {
            outEdges(i) = e
            return
          }
        }
        ({
          i += 1; i
        })
      }
    }
    edges(v) = new Array[Edge](outEdges.length * 2)
    System.arraycopy(outEdges, 0, edges(v), 0, outEdges.length)
    _insert(e, v)
  }

  def remove(e: Edge) {
    _remove(e, e.v)
    if (!isDigraph) {
      _remove(e, e.w)
    }
    nEdges -= 1
  }

  def getEdgeIterator(v: Int): EdgeIterator = {
    val outEdges: Array[Edge] = edges(v)
    new MyEdgeIterator(outEdges.indexWhere((e: Edge) => e != null), outEdges)
  }

  private def _remove(e: Edge, v: Int) {
    val outEdges: Array[Edge] = edges(v)
    val idx = outEdges.indexOf(e)
    if (idx >= 0) outEdges(idx) = null
  }

  def getEdgesIterator(v: Int): Iterator[Edge] = {
    val outEdges: Array[Edge] = edges(v)
    if (outEdges == null) return new EmptyIterator[Edge]
    SparseGraphImpl.getEdgesIterator(outEdges)
  }

  def getEdges(v: Int): Iterable[Edge] = {
    new Iterable[Edge] {
      def iterator: Iterator[Edge] = {
        getEdgesIterator(v)
      }
    }
  }

  def getDfs: Dfs = {
    new Dfs {
      def depthFirstSearch(visitor: DfsVisitor) {
        DepthFirstSearch.depthFirstSearch(SparseGraphImpl.this, visitor)
      }
    }
  }
}

object SparseGraphImpl {
  private class MyEmptyEdgeIterator extends EdgeIterator {
    override def clone: MyEmptyEdgeIterator = {
      new MyEmptyEdgeIterator()
    }

    def edge(): Edge = {
      throw new UnsupportedOperationException("Method edge not implemented in " + getClass)
    }

    def advance() {
      throw new UnsupportedOperationException("Method advance not implemented in " + getClass)
    }

    def hasNext: Boolean = {
      false
    }

    def isAfterEnd: Boolean = {
      throw new UnsupportedOperationException("Method isAfterEnd not implemented in " + getClass)
    }
  }

  class EmptyIterator[E] extends Iterator[E] {
    def hasNext: Boolean = false

    def next(): E = {
      throw new UnsupportedOperationException("Method next not implemented in " + getClass)
    }

    def remove() {
      throw new UnsupportedOperationException("Method remove not implemented in " + getClass)
    }
  }

  private class MyEdgeIterator(first: Int, anEdges: Array[Edge]) extends EdgeIterator {
    private var curEdge: Int = first
    private final val edges: Array[Edge] = anEdges

    override def clone: MyEdgeIterator = {
      new MyEdgeIterator(curEdge, edges)
    }

    def edge(): Edge = edges(curEdge)

    def advance() {
      curEdge = edges.indexWhere((e: Edge) => e != null, curEdge)
    }

    def hasNext: Boolean = {
      if (isAfterEnd) return false
      edges.indexWhere((e: Edge) => e != null, curEdge) >= 0
    }

    def isAfterEnd: Boolean = curEdge < 0
  }

  private[impl] def getEdgesIterator(edges: Array[Edge]): Iterator[Edge] = edges.filter(_ != null).iterator
}