import Figures._

package object Solver {

  /**
   * Created by adam on 17.04.2015.
   */
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
    val sorted = figures.sortBy { case (_, Figure(priority, _)) => -priority }
    solveAcc(M, N, sorted, List())
  }

  private def solveAcc(M: Int, N: Int, figures: List[(Int, Figure)], placements: List[FigurePlacement]): List[Solution] = {
    val board = (1 to M) map (x => (1 to N) map (y => (x, y))) flatMap (x => x)


    println(board)
    List()
  }

  def main(args: Array[String]) {
    //7×7 board with 2 Kings, 2 Queens, 2 Bishops and 1 Knight
    solve(7, 7, List((2, King), (2, Queen), (2, Bishop), (1, Knight)))
  }
}