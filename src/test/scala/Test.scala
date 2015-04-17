import org.scalatest.{Matchers, FunSuite}
import Figures._

/**
 * Created by adam on 17.04.2015.
 */

class Test extends FunSuite with Matchers {

  test("example with 3x3, 2 Kings, 1 Rook") {
    val result = Solver.solve(3, 3, List((2, King), (2, Rook)))

    result should have size 4

    result should contain
    """K-K
      |---
      |-R-""".stripMargin

    result should contain
    """K--
      |--R
      |k--""".stripMargin

    result should contain
    """--K
      |R--
      |--K""".stripMargin

    result should contain
    """-R-
      |---
      |K-K""".stripMargin
  }

  test("Rook board cover") {
    val result = Rook.getCovered(Dimension(3, 3), Position(2, 2))

    result should have size 5
    result should contain allOf(Position(2, 2), Position(1, 2), Position(3, 2), Position(2, 3), Position(2, 1))
  }
}
