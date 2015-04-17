/**
 * Created by adam on 17.04.2015.
 */
package object Figures {

  case class Position(x: Int, y: Int) {
    def up = Position(x + 1, y)

    def down = Position(x - 1, y)

    def left = Position(x, y - 1)

    def right = Position(x, y + 1)
  }

  case class Dimension(x: Int, y: Int)

  case class FigurePlacement(figure: Figure, position: Position)

  def trim(dimension: Dimension, positions: List[Position]) = {
    positions.filter(pos => pos.x > 0 && pos.x <= dimension.x && pos.y > 0 && pos.y <= dimension.y)
  }

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
      trim(dimension, List(position.up, position.down, position.left, position.right,
        position.left.up, position.left.down, position.right.up, position.right.down))
    }
  }

  object Rook extends Figure(3, "R") {
    def getCovered(dimension: Dimension, position: Position): List[Position] = {
      lazy val up: Stream[Position] = position.up #:: up.map(p => p.up)
      lazy val down: Stream[Position] = position.down #:: down.map(p => p.down)
      lazy val left: Stream[Position] = position.left #:: left.map(p => p.left)
      lazy val right: Stream[Position] = position.right #:: right.map(p => p.right)

      position :: up.takeWhile(pos => pos.x <= dimension.x).toList ++
        down.takeWhile(pos => pos.x > 0).toList ++
        left.takeWhile(pos => pos.y > 0).toList ++
        right.takeWhile(pos => pos.y <= dimension.y).toList
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
      trim(dimension, List(
        position.up.up.left, position.up.up.right,
        position.left.left.up, position.left.left.down,
        position.right.right.up, position.right.right.down,
        position.down.down.left, position.down.down.right)
      )
    }
  }

}