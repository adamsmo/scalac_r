/**
 * Created by adam on 17.04.2015.
 */
package object Figures {

  case class CoveredField(x: Int, y: Int)

  case class Position(x: Int, y: Int)

  case class Dimension(x: Int, y: Int)

  abstract class Figure() {
    def getCovered(dimension: Dimension, position: Position): List[CoveredField]
  }

  object King extends Figure {
    def getCovered(dimension: Dimension, position: Position): List[CoveredField] = {
      List()
    }
  }

  object Rook extends Figure {
    def getCovered(dimension: Dimension, position: Position): List[CoveredField] = {
      List()
    }
  }

}