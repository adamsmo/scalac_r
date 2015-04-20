import org.scalatest.{Matchers, FunSuite}
import Figures._
import Solver._

/**
 * Created by adam on 17.04.2015.
 */

class Test extends FunSuite with Matchers {


  test("example with 3x3, 2 Kings, 2 Rook") {
    val result = Solver.solve(3, 3, List((2, King), (2, Rook)))
    result should have size 0
  }

  test("example with 3x3, 2 Kings, 1 Rook") {
    val result = Solver.solve(3, 3, List((2, King), (1, Rook)))

    result should have size 4

    result map(printFigures(_, Board(3,3))) should contain allOf(
      """K-K
        |---
        |-R-""".stripMargin,
      """K--
        |--R
        |K--""".stripMargin,
      """--K
        |R--
        |--K""".stripMargin,
      """-R-
        |---
        |K-K""".stripMargin
      )

  }

  test("Rook board cover") {
    val result = Rook.getCovered(Board(3, 3), Position(2, 2))

    result should have size 5
    result should contain allOf(Position(2, 2), Position(1, 2), Position(3, 2), Position(2, 3), Position(2, 1))
  }

  test("Rook board cover corner") {
    val result = Rook.getCovered(Board(3, 3), Position(1, 3))

    result should have size 5
    result should contain allOf(Position(1, 3), Position(1, 2), Position(1, 1), Position(2, 3), Position(3, 3))
  }

  test("Bishop board cover") {
    val result = Bishop.getCovered(Board(3, 3), Position(2, 2))

    result should have size 5
    result should contain allOf(Position(2, 2), Position(1, 1), Position(3, 1), Position(1, 3), Position(3, 3))
  }

  test("Test board generation") {
    val result = Board(3, 3).positions

    val expected = List(
      Position(1, 1), Position(1, 2), Position(1, 3),
      Position(2, 1), Position(2, 2), Position(2, 3),
      Position(3, 1), Position(3, 2), Position(3, 3))

    result should contain theSameElementsAs expected
  }

  test("Queen board cover") {
    val board = Board(5, 5)
    val result = Queen.getCovered(board, Position(3, 3))

    val notCoveredByQueen = List(
      Position(2, 1),
      Position(4, 1),
      Position(1, 2),
      Position(1, 4),
      Position(5, 2),
      Position(5, 4),
      Position(2, 5),
      Position(4, 5))

    val expected = board.positions.filter(!notCoveredByQueen.contains(_))

    result should have size 17
    result should contain theSameElementsAs expected
  }

  test("King board cover") {
    val result = King.getCovered(Board(5, 5), Position(2, 2))

    result should have size 9
    result should contain allOf(
      Position(1, 1), Position(1, 2), Position(1, 3),
      Position(2, 1), Position(2, 2), Position(2, 3),
      Position(3, 1), Position(3, 2), Position(3, 3)
      )
  }

  test("Knight board cover") {
    val result = Knight.getCovered(Board(5, 5), Position(3, 3))

    result should have size 9
    result should contain allOf(
      Position(3, 3),
      Position(2, 1),
      Position(4, 1),
      Position(1, 2),
      Position(1, 4),
      Position(5, 2),
      Position(5, 4),
      Position(2, 5),
      Position(4, 5)
      )
  }

  test("Allowed Kings combinations check") {
    val result = Solver.combinationAllowed(King, Board(3, 3), List())(List(Position(1, 1), Position(3, 3)))
    result shouldBe true
  }

  test("Not allowed Kings combinations check") {
    val result = Solver.combinationAllowed(King, Board(3, 3), List())(List(Position(1, 1), Position(2, 2)))
    result shouldBe false
  }

}
