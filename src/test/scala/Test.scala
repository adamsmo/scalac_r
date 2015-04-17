import org.scalatest.FunSuite
import Figures._

/**
 * Created by adam on 17.04.2015.
 */

class Test extends FunSuite {

  test("sentenceOccurrences: abcd e") {
    val result = Solver.solve(3, 3, List((King, 2), (Rook, 1)))
    val expected = List(
      """K-K
        |---
        |-R-""".stripMargin,

      """K--
        |--R
        |k--""".stripMargin,

      """K--
        |--R
        |K--""".stripMargin,

      """K-K
        |---
        |-R-""".stripMargin
    )
    assert(result === expected)
  }
}
