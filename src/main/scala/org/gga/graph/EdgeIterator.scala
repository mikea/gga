package org.gga.graph

/**
 * @author mike
 */
trait EdgeIterator {
//  override def clone: EdgeIterator

  def edge(): Edge

  def advance()

  def hasNext: Boolean

  def isAfterEnd: Boolean
}