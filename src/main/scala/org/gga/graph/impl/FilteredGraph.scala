package org.gga.graph.impl

import org.gga.graph.Edge
import org.gga.graph.Graph
import org.gga.graph.search.dfs.DepthFirstSearch
import org.gga.graph.search.dfs.Dfs
import org.gga.graph.search.dfs.DfsVisitor

/**
 * @author mike
 */
class FilteredGraph(aGraph: Graph, aVertices: Array[Boolean], anEdges: Array[Boolean]) extends Graph {
  private final val verticesMap: Array[Boolean] = aVertices
  private final val edges: Array[Boolean] = anEdges
  private final val g: Graph = aGraph

  def this(g: Graph, filter: FilteredGraph.GraphFilter) = {
    this(g,
      FilteredGraph.getVerticesArray(g, filter),
      FilteredGraph.getEdgesArray(g, filter))
  }

  def V: Int = g.V

  def E: Int = g.E

  def isDirected: Boolean = g.isDirected

  def edge(v: Int, w: Int): Option[Edge] = {
    if (!verticesMap(v) || !verticesMap(w)) return None
    val edge: Edge = g.edge(v, w).get
    if (edge != null && edges(edge.idx)) Some(edge) else None
  }

  def getEdgeIterator(v: Int): Nothing = {
    throw new UnsupportedOperationException("Method getEdgeIterator not implemented in " + getClass)
  }

  def getEdgesIterator(v: Int): Iterator[Edge] = {
    if (!verticesMap(v)) return new SparseGraphImpl.EmptyIterator[Edge]

    g.getEdges(v).filter((edge: Edge) => edges(edge.idx) && verticesMap(edge.w)).iterator
  }

  def getDfs: Dfs = {
    new Dfs {
      def depthFirstSearch(visitor: DfsVisitor) {
        DepthFirstSearch.depthFirstSearch(FilteredGraph.this, visitor)
      }
    }
  }

  def getEdges(v: Int): Iterable[Edge] = {
    new Iterable[Edge] {
      def iterator: Iterator[Edge] = {
        getEdgesIterator(v)
      }
    }
  }
}

object FilteredGraph {

  trait GraphFilter {
    def acceptVertex(v: Int): Boolean

    def acceptEdge(e: Edge): Boolean
  }

  private def getVerticesArray(g: Graph, filter: GraphFilter): Array[Boolean] = {
    Range(0, g.V).map((v: Int) => filter.acceptVertex(v)).toArray
  }


  private def getEdgesArray(g: Graph, filter: GraphFilter): Array[Boolean] = {
    val result: Array[Boolean] = new Array[Boolean](g.E)

    for (v <- Range(0, g.V)) {
      for (e <- g.getEdges(v)) {
        result(e.idx()) = filter.acceptEdge(e)
      }
    }

    result
  }
}