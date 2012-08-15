package org.gga.graph

/**
 * Edge from v to w.
 *
 * @author mike
 */
class Edge(aFrom: Int, aTo: Int) {
  val v: Int = aFrom
  val w: Int = aTo
  private var idx: Int = 0

  def target = w
  def source = v

  def getIdx: Int = idx

  def setIdx(idx: Int) {
    this.idx = idx
  }

  def other(t: Int): Int = if (from(t)) w else v

  def from(t: Int): Boolean = t == v

  override def toString: String = "(" + v + " -> " + w + ")"

  override def equals(other: Any): Boolean = {
    other match {
      case that: Edge => v == that.v && w == that.w
      case _ => false
    }
  }

  override def hashCode: Int = v * 31 + w
}