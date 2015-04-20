package chess

import Figures._
import Solver._

/**
 * Created by adam on 20.04.2015.
 */
object App {

  case class Config(M: Int = 0, N: Int = 0, countOnly: Boolean = false, figures: Map[String, Int] = Map())

  val parser = new scopt.OptionParser[Config]("chess") {
    head("\nexample call for printing solutions:\n", "[sbt] run --N 4 --M 4 -f Q=2,N=1\n",
      "[cli] java -jar <jar_file.jar> --N 4 --M 4 -f Q=2,N=1\n",
      "\nexample call for counting solutions:\n", "[sbt] run --N 7 --M 7 -c -f K=2,Q=2,B=2,N=1\n",
      "[cli] java -jar <jar_file.jar> --N 7 --M 7 -c -f K=2,Q=2,B=2,N=1\n")
    opt[Int]("M") required() action { (x, c) =>
      c.copy(M = x)
    } validate { value =>
      if (value <= 0)
        failure("height of board must be > 0")
      else
        success
    } text "M is height of board"

    opt[Int]("N") required() action { (x, c) =>
      c.copy(N = x)
    } validate { value =>
      if (value <= 0)
        failure("width of board must be > 0")
      else
        success
    } text "N is width of board"

    opt[Unit]('c', "countOnly") action { (_, c) =>
      c.copy(countOnly = true)
    } text "instead of printing all results prints only number of results, and amount of time that calculations took"

    opt[Map[String, Int]]('f', "figures") required() valueName "<figure_name>=<count>" action { (x, c) =>
      c.copy(figures = x)
    } validate { x =>
      if (x.values.exists(_ <= 0))
        failure("Value must be > 0")
      else if (x.keys.exists(k => !allFigures.contains(k)))
        failure("Figure name must be one of: [" + allFigures.keys.mkString(", ") + "]")
      else
        success
    } text ("Figure name followed by count, single figure can occur only once \n" +
      "        example: -f K=1,Q=2...\n" +
      "        available figure names" + "[" + allFigures.keys.mkString(", ") + "]\n" +
      "        N - Knight\n" +
      "        Q - Queen\n" +
      "        B - Bishop\n" +
      "        K - King\n" +
      "        R - Rook\n"
      )

    help("help") text "prints this usage text"

    override def showUsageOnError = true

  }

  def main(args: Array[String]) {
    parser.parse(args, Config()) match {
      case Some(config) =>
        val figs = config.figures.toList.map { case (name, count) => (count, allFigures(name)) }

        val t0 = System.nanoTime()
        val solution = solve(config.M, config.N, figs)
        val t1 = System.nanoTime()

        if (config.countOnly) {
          println(solution.size + "")
          println("Elapsed time: " + ((t1 - t0) / 1000000000) + "s")
        } else {
          solution.foreach(s => println("\n" + printFigures(s, Board(config.M, config.N))))
        }
      case None =>
    }
  }
}
