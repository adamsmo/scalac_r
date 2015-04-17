import Figures._
import com.typesafe.scalalogging._

import scala.collection.Map

/**
 * Created by adam on 17.04.2015.
 */
package object Solver extends LazyLogging {

  type Solution = String

  /**
   *
   * @param M - width of the board
   * @param N - height of the board
   * @param figures
   * @return
   */
  def solve(M: Int, N: Int, figures: List[(Int, Figure)]): List[Solution] = {


    //first pic figures that covers larger area of the board
    //val sorted = figFlat.sortBy(-_.priority)

    solveAcc(Dimension(M, N), figures, List())
  }

  private def possible(dimension: Dimension, fig: Figure, position: Position, placements: List[Position]): Boolean =
    fig.getCovered(dimension, position) find (pos => placements.contains(pos)) match {
      case Some(_) => false
      case None => true
    }

  private def solveAcc(dimension: Dimension, figures: List[(Int, Figure)], placements: List[FigurePlacement]): List[Solution] = {

    val figFlat = for (
      (count, f) <- figures;
      i <- 1 to count
    ) yield f

    val newPlacements = for (
      x <- 1 to dimension.x;
      y <- 1 to dimension.y;
      fig <- figFlat if possible(dimension, fig, Position(x, y), placements.map(_.position))
    ) yield FigurePlacement(fig, Position(x, y))

    val figCount: Map[Figure, Int] = figures.map { case (count, fig) => fig -> count }.toMap

    val figurePositionMap = newPlacements.toSet.toList
      .groupBy((fp: FigurePlacement) => fp.figure)
      .filter { case (f, set) => figCount.contains(f) && figCount.get(f).get <= set.size }
      .map { case (fig, figPlacement) => figPlacement.combinations(figCount(fig)).toList }

    //group by figure type
    logger.debug(figures.toString())
    logger.debug(figurePositionMap.toString())

    List()
  }

  def main(args: Array[String]) {
    //7×7 board with 2 Kings, 2 Queens, 2 Bishops and 1 Knight
    solve(7, 7, List((2, King), (2, Queen), (2, Bishop), (1, Knight)))
  }
}