package scala2021.erusak.task08

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import scala2021.erusak.task08.BowlingGame.{AttemptsWrapper, ExtraFromSpare, ExtraFromStrike, Hit, Spare, Strike}

class BowlingScoreSuite extends AnyFlatSpec {

  private val TaskId = "Task 08 (Bowling Game Scoring)"

  it should s"$TaskId decode string to strongly typed presentation" in {
    "X|12|5/|-/||5".toAttempts shouldBe List(Strike, Hit(1, 2), Spare(5), Spare(0), ExtraFromSpare(5))
    "X|12|5/|X||X3".toAttempts shouldBe List(Strike, Hit(1, 2), Spare(5), Strike, ExtraFromStrike(10, 3))
    "X|12|5/|12||".toAttempts shouldBe List(Strike, Hit(1, 2), Spare(5), Hit(1,2))
    "X|12|5/|X||2/".toAttempts shouldBe List(Strike, Hit(1, 2), Spare(5), Strike, ExtraFromStrike(2, 8))
    "X|7/|9-|X|-8|8/|-6|X|X|X||81".toAttempts shouldBe List(Strike, Spare(7), Hit(9,0), Strike, Hit(0,8), Spare(8), Hit(0,6), Strike, Strike, Strike, ExtraFromStrike(8,1))
  }

  it should s"$TaskId calculate the score of the Bowling Game correctly" in {
    "X|X|X|X|X|X|X|X|X|X||XX".calculateScore shouldBe 300
    "9-|9-|9-|9-|9-|9-|9-|9-|9-|9-||".calculateScore shouldBe 90
    "5/|5/|5/|5/|5/|5/|5/|5/|5/|5/||5".calculateScore shouldBe 150
    "X|7/|9-|X|-8|8/|-6|X|X|X||81".calculateScore shouldBe 167
    "51|5-|X|54|6/|27|-/|8/|X|5/||X".calculateScore shouldBe 138
  }
}
