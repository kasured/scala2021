package scala2021.erusak.task08

import scala.annotation.tailrec

object BowlingGame {

  implicit class AttemptsWrapper(input: String) {
    def calculateScore: Int = BowlingGame.calculateScore(input.toAttempts)

    def toAttempts: List[Attempt] = {
      val split = input.split("\\|\\|").toList
      val normalAttempts = split.head.split('|').toList.map(Attempt.apply)
      val extraAttempts = if (split.size == 2) List(Attempt.extra(split(1))) else List.empty
      normalAttempts ::: extraAttempts
    }
  }

  private def calculateScore(attempts: List[Attempt]): Int = {
    val reversed = attempts.reverse
    calculateScore(reversed, 0, 0, 0)
  }

  @tailrec
  private def calculateScore(attemptsReversed: List[Attempt], beforeBefore: Int, before: Int, acc: Int): Int = attemptsReversed match {
    case Nil => acc
    case ExtraFromStrike(first, second) :: tail => calculateScore(tail, second, first, acc)
    case ExtraFromSpare(first) :: tail => calculateScore(tail, before, first, acc)
    case Hit(p1, p2) :: tail => calculateScore(tail, p2, p1, acc + p1 + p2)
    case Strike :: tail => calculateScore(tail, before, 10, acc + 10 + before + beforeBefore)
    case Spare(first) :: tail => calculateScore(tail, 10 - first, first, acc + 10 + before)
  }

  sealed trait Attempt
  final case object Strike extends Attempt
  final case class Spare(first: Int) extends Attempt
  final case class Hit(first: Int, second: Int) extends Attempt
  final case class ExtraFromSpare(first: Int) extends Attempt
  final case class ExtraFromStrike(first: Int, second: Int) extends Attempt

  object Attempt {

    def apply(attemptRaw: String): Attempt = attemptRaw match {
      case "X" => Strike
      case spare if spare.endsWith("/") => Spare(asDigit(spare.charAt(0)))
      case hit => hit.toList match {
        case List(d1, d2) => Hit(asDigit(d1), asDigit(d2))
        case unknown => throw new IllegalArgumentException(s"cannot proceed with $unknown")
      }
    }

    def extra(extraRaw: String): Attempt = extraRaw.toList match {
      case 'X' :: Nil => ExtraFromSpare(10)
      case d :: Nil => ExtraFromSpare(asDigit(d))
      case 'X' :: 'X' :: Nil => ExtraFromStrike(10, 10)
      case 'X' :: d :: Nil => ExtraFromStrike(10, asDigit(d))
      case d :: '/' :: Nil => ExtraFromStrike(asDigit(d), 10 - asDigit(d))
      case d1 :: d2 :: Nil => ExtraFromStrike(asDigit(d1), asDigit(d2))
      case unknown => throw new IllegalArgumentException(s"cannot proceed with $unknown")
    }

    def asDigit(char: Char): Int = char match {
      case '-' => 0
      case d => d.asDigit
    }

  }

}
