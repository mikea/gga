package org.gga.graph.search.dfs

/**
 * @author mike.aizatsky@gmail.com
 */
trait Dfs {
  def depthFirstSearch(visitor: DfsVisitor)
  def depthFirstSearch(startVertex: Int, visitor: DfsVisitor)
}