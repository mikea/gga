package org.gga.graph

/**
 * @author mike.aizatsky@gmail.com
 */
trait MutableGraph extends Graph {
  def insert(v: Int, w: Int): Edge

  def remove(e: Edge)
}