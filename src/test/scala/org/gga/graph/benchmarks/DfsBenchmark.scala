package org.gga.graph.benchmarks

import com.google.caliper.Runner
import com.google.caliper.SimpleBenchmark
import org.gga.graph.Graph
import org.gga.graph.io.GraphIo
import org.gga.graph.search.dfs.AbstractDfsVisitor

/**
 * @author mike.aizatsky@gmail.com
 */
object DfsBenchmark extends App {
  Runner.main(classOf[DfsBenchmark], args)
}

class DfsBenchmark extends SimpleBenchmark {
  protected override def setUp() {
    super.setUp()
    graph = GraphIo.readGraph("testData/graph1.g")
  }

  def timeEmptyVisitor(reps: Int) {
    val visitor: AbstractDfsVisitor = new AbstractDfsVisitor() {}

    for (i <- 1 to reps) {
      graph.getDfs.depthFirstSearch(visitor)
    }
  }

  private var graph: Graph = null
}