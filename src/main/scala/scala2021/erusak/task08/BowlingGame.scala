package scala2021.erusak.task08

object BowlingGame {

  implicit class AttemptsWrapper(input: String) {
    def calculateScore: Int = BowlingGame.calculateScore(input.toAttempts)

    def toAttempts: List[Attempt] = input.split('|').toList.flatMap(decode)

    private def decode(input: String): List[Attempt] = input match {
      case "" => List(Extra)
      case other => other.toList.map(Attempt.apply)
    }

  }

  sealed trait Attempt

  case object Strike extends Attempt

  case object Spare extends Attempt

  case object Miss extends Attempt

  case object Extra extends Attempt

  case class Hit(pins: Int) extends Attempt

  object Attempt {

    def apply(char: Char): Attempt = char match {
      case 'X' => Strike
      case '/' => Spare
      case '-' => Miss
      case n => Hit(n.asDigit)
    }

  }

  private def calculateScore(attempts: List[Attempt]): Int = {
    println(attempts)
    val tmp = attempts.sliding(3).flatMap(processExtra)
    val withExtraRemoved = tmp.toList
    println(withExtraRemoved)
    withExtraRemoved.sliding(3).map(evaluate).sum
  }

  private def processExtra(frame: List[Attempt]): List[Attempt] = frame match {
    case Extra :: Nil => List(Miss, Miss)
    case Extra :: attempt :: Nil => List(attempt, Miss)
    case Extra :: attempt1 :: attempt2 :: Nil => List(attempt1, attempt2)
    case head :: _ => List(head)
    case _ => Nil
  }

  private def evaluate(frame: List[Attempt]): Int = frame match {
    case _ => 0
  }

}
