package scala2021.erusak.task08

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import scala2021.erusak.task08.BowlingGame.{AttemptsWrapper, Extra, Hit, Miss, Spare, Strike}

class BowlingScoreSuite extends AnyFlatSpec {

  private val TaskId = "Task 08 (Bowling Game Scoring)"

  it should s"$TaskId decode string to strongly typed presentation" in {
    "X|12|5/|-X||55".toAttempts shouldBe List(Strike, Hit(1), Hit(2), Hit(5), Spare, Miss, Strike, Extra, Hit(5), Hit(5))
  }

  it should s"$TaskId calculate the score of the Bowling Game correctly" in {
    "X|X|X|X|X|X|X|X|X|X||XX".calculateScore shouldBe 300
    "9-|9-|9-|9-|9-|9-|9-|9-|9-|9-||".calculateScore shouldBe 90
    "5/|5/|5/|5/|5/|5/|5/|5/|5/|5/||5".calculateScore shouldBe 150
    "X|7/|9-|X|-8|8/|-6|X|X|X||81".calculateScore shouldBe 167
  }
}
