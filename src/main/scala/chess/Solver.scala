import Figures._
import com.typesafe.scalalogging._

/**
 * Created by adam on 17.04.2015.
 */
package object Solver extends LazyLogging {

  type Solution = List[FigurePlacement]

  /**
   *
   * @param M - width of the board
   * @param N - height of the board
   * @param figures
   * @return
   */
  def solve(M: Int, N: Int, figures: List[(Int, Figure)]): List[Solution] = {
    //first pic figures that covers larger area of the board
    solveAcc(Board(M, N), figures.sortBy { case (_, fig) => -fig.priority }, List(), Set())
  }

  def combinationAllowed(figure: Figure, board: Board, placements: List[Position])(positions: Seq[Position]): Boolean = {
    !positions.exists(posToCheck =>
      figure.getCovered(board, posToCheck).exists(covered =>
        (positions.contains(covered) && covered != posToCheck) || placements.contains(covered)))
  }

  private def solveAcc(board: Board, figures: List[(Int, Figure)],
                       placements: List[FigurePlacement], coveredFields: Set[Position]): List[Solution] =
    figures match {

      case (count, fig) :: restOfFigures =>
        val placementsAsPositions = placements.map(p => p.position)

        val possiblePlacements = board.positions.filter(!coveredFields.contains(_))
        val allowedFiguresPlacement = possiblePlacements.combinations(count)
          .filter(combinationAllowed(fig, board, placementsAsPositions))

        if (allowedFiguresPlacement.isEmpty) {
          Nil
        } else {
          val solved = for (
            placement <- allowedFiguresPlacement
          ) yield solveAcc(board, restOfFigures, placement.map(FigurePlacement(fig, _)).toList ++ placements,
              placement.flatMap(fig.getCovered(board, _)).toSet ++ coveredFields)

          solved.flatMap(b => b).toList
        }

      case Nil => List(placements)

    }

  def printFigures(s: Solution, b: Board) = {
    val posToFigMap = s.map(fp => fp.position -> fp.figure).toMap

    val lines = for (
      x <- 1 to b.x

    ) yield {
        val line = for (
          y <- 1 to b.y
        ) yield {
            posToFigMap.get(Position(x, y)) match {
              case Some(fig: Figure) => fig.short
              case None => "-"
            }
          }

        line.mkString("")
      }

    lines.mkString("\n")
  }

  def main(args: Array[String]) {
    //7×7 board with 2 Kings, 2 Queens, 2 Bishops and 1 Knight

    val t0 = System.nanoTime()
    logger.debug(solve(7, 7, List((2, King), (2, Queen), (2, Bishop), (1, Knight))).size + "")
    val t1 = System.nanoTime()
    logger.debug("Elapsed time: " + ((t1 - t0) / 1000000000) + "s")
  }
}