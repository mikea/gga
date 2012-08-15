package org.gga.graph.util

import java.util.Arrays

/**
 * @author mike
 */
object ArrayUtil {
  def arrayToString[E](array: Array[E]): String = iterableToString(array.toIterable)

  def iterableToString[E](it: Iterable[E]): String = it.mkString("[", ", ", "]")

  def max(array: Array[Int]): Int = {
    if (array.length == 0) return Integer.MIN_VALUE
    var result: Int = array(0)
    for (v <- array) {
      result = Math.max(result, v)
    }
    result
  }

/*
  def reverseMap(map: Array[Int]): Array[Int] = {
    val max: Int = max(map)
    val result: Array[Int] = new Array[Int](max + 1)
    Arrays.fill(result, -1)
    {
      var i: Int = 0
      while (i < map.length) {
        {
          if (map(i) >= 0) {
            result(map(i)) = i
          }
        }
        ({
          i += 1; i
        })
      }
    }
    return result
  }
*/
}