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

  private def calculateScore(attempts: List[Attempt]): Int = attempts.sliding(3).map(attemptScore).sum

  private def attemptScore(frame: List[Attempt]): Int = frame match {
    case _ => 0
  }

}
