/**
 * Created by adam on 17.04.2015.
 */
package object Figures {

  case class Position(x: Int, y: Int)

  case class Dimension(x: Int, y: Int)

  case class FigurePlacement(figure: Figure, position: Position)

  /**
   *
   * @param priority - lower priority means that Figure covers smaller area of board
   */
  abstract case class Figure(priority: Int, short: String) {
    def getCovered(dimension: Dimension, position: Position): List[Position]

    override def toString = short
  }

  object King extends Figure(0, "K") {
    def getCovered(dimension: Dimension, position: Position): List[Position] = {
      List()
    }
  }

  object Rook extends Figure(3, "R") {
    def getCovered(dimension: Dimension, position: Position): List[Position] = {
      List()
    }
  }

  object Queen extends Figure(4, "Q") {
    def getCovered(dimension: Dimension, position: Position): List[Position] = {
      List()
    }
  }

  object Bishop extends Figure(2, "B") {
    def getCovered(dimension: Dimension, position: Position): List[Position] = {
      List()
    }
  }

  object Knight extends Figure(1, "N") {
    def getCovered(dimension: Dimension, position: Position): List[Position] = {
      List()
    }
  }

}