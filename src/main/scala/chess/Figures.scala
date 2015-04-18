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

  case class Dimension(x: Int, y: Int) {
    def inBound(pos: Position): Boolean = pos.x > 0 && pos.x <= x && pos.y > 0 && pos.y <= y
  }

  case class FigurePlacement(figure: Figure, position: Position)

  def trim(dimension: Dimension, positions: List[Position]) = {
    positions.filter(dimension.inBound)
  }

  /**
   *
   * @param priority - lower priority means that Figure covers smaller area of board
   */
  abstract case class Figure(priority: Int, short: String) {
    def getCovered(dimension: Dimension, position: Position): List[Position]

    def positions(position: Position, direction: Position => Position): Stream[Position] = {
      lazy val result: Stream[Position] = direction(position) #:: result.map(direction)
      result
    }

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
      position :: positions(position, _.up).takeWhile(dimension.inBound).toList ++
        positions(position, _.down).takeWhile(dimension.inBound).toList ++
        positions(position, _.left).takeWhile(dimension.inBound).toList ++
        positions(position, _.right).takeWhile(dimension.inBound).toList
    }
  }

  object Queen extends Figure(4, "Q") {
    def getCovered(dimension: Dimension, position: Position): List[Position] = {
      position :: positions(position, _.up).takeWhile(dimension.inBound).toList ++
        positions(position, _.down).takeWhile(dimension.inBound).toList ++
        positions(position, _.left).takeWhile(dimension.inBound).toList ++
        positions(position, _.right).takeWhile(dimension.inBound).toList ++
        positions(position, _.up.left).takeWhile(dimension.inBound).toList ++
        positions(position, _.up.right).takeWhile(dimension.inBound).toList ++
        positions(position, _.down.left).takeWhile(dimension.inBound).toList ++
        positions(position, _.down.right).takeWhile(dimension.inBound).toList
    }
  }

  object Bishop extends Figure(2, "B") {
    def getCovered(dimension: Dimension, position: Position): List[Position] = {
      position :: positions(position, _.up.left).takeWhile(dimension.inBound).toList ++
        positions(position, _.up.right).takeWhile(dimension.inBound).toList ++
        positions(position, _.down.left).takeWhile(dimension.inBound).toList ++
        positions(position, _.down.right).takeWhile(dimension.inBound).toList
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