package chess

import Figures._
import Solver._
import ArgsParser._

/**
 * Created by adam on 20.04.2015.
 */
object App {

  def main(args: Array[String]) {
    parser.parse(args, Config()) match {
      case Some(config) =>
        val figs = config.figures.toList.map { case (name, count) => (count, allFigures(name)) }

        val before = System.nanoTime()
        val solution = solve(config.M, config.N, figs)
        val after = System.nanoTime()

        if (config.countOnly) {
          println(solution.size + "")
          println("Elapsed time: " + ((after - before) / 1000000000) + "s")
        } else {
          solution.foreach(s => println("\n" + printFigures(s, Board(config.M, config.N))))
        }
      case None =>
    }
  }
}
