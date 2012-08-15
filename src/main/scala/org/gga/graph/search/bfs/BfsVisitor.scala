package org.gga.graph.search.bfs

import org.gga.graph.Edge
import org.gga.graph.Graph

/**
 * @author mike
 */
trait BfsVisitor {
  /**
   * This is invoked on every vertex of the graph before the start of the graph search.
   */
  def initializeVertex(v: Int, g: Graph)

  /**
   * This is invoked when a vertex is encountered for the first time.
   */
  def discoverVertex(v: Int, g: Graph)

  /**
   * This is invoked on a vertex as it is popped from the queue. This happens immediately before examine_edge() is
   * invoked on each of the out-edges of vertex u.
   */
  def examineVertex(v: Int, g: Graph)

  /**
   * This invoked on a vertex after all of its out edges have been added to the search tree and all of the adjacent
   * vertices have been discovered (but before the out-edges of the adjacent vertices have been examined).
   */
  def finishVertex(v: Int, g: Graph)

  /**
   * This is invoked on every out-edge of each vertex after it is discovered.
   */
  def examineEdge(e: Edge, g: Graph)

  /**
   * This is invoked on each edge as it becomes a member of the edges that form the search tree.
   */
  def treeEdge(e: Edge, g: Graph)

  /**
   * This is invoked on back or cross edges for directed graphs and cross edges for undirected graphs.
   */
  def nonTreeEdge(e: Edge, g: Graph)

  /**
   * This is invoked on the subset of non-tree edges whose target vertex is colored gray at the time of examination.
   * The color gray indicates that the vertex is currently in the queue.
   */
  def grayTarget(e: Edge, g: Graph)

  /**
   * This is invoked on the subset of non-tree edges whose target vertex is colored black at the time of examination.
   * The color black indicates that the vertex has been removed from the queue.
   */
  def blackTarget(e: Edge, g: Graph)
}