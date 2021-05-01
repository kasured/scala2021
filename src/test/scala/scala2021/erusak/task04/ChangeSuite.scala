package scala2021.erusak.task04

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.{convertToAnyShouldWrapper, equal}
import org.scalatest.prop.TableDrivenPropertyChecks.{Table, forAll}

class ChangeSuite extends AnyFlatSpec {
  private val TaskId = "Task 04 (Change)"

  private val testCases =
    Table(
      ("coins", "change", "isChangePossible"),
      (List(2, 4, 6), 8, true),
      (List(2, 4, 6), 5, false),
      (List(2), 5, false),
      (List.empty[Int], 1, false),
      (List.empty[Int], 0, true),
      (List(2, 3, 6, 7), 12, true),
      (List(8, 3, 1, 2), 3, true),
      (List(2, 5, 3, 6), 10, true),
      (List(25, 10, 5), 30, true),
      (List(1, 5, 9, 6), 11, true),
      (List(10,3), 13, true),
      (List(10,3), 22, true),
      (List(10,3), 17, false)
    )

  it should s"${TaskId}: correctly identify if change is possible" in {
    forAll (testCases) { (coins: List[Int], change: Int, isChangePossible: Boolean) =>
      Change.isChangePossible(change, coins) should equal (isChangePossible)
    }
  }

  private val gcdTestCases =
    Table(
      ("a", "b", "gcd"),
      (1, 0, 1),
      (1, 5, 1),
      (2, 3, 1),
      (8, 10, 2),
      (11, 13, 1),
      (10, 20, 10),
      (8, 12, 4)
    )

  it should s"${TaskId}: correctly find gcd for two numbers" in {
    forAll (gcdTestCases) { (a: Int, b: Int, gcd: Int) =>
      Change.gcd(a, b) should equal (gcd)
    }
  }

  private val gcdMultipleTestCases =
    Table(
      ("input", "gcd"),
      (List(1,2,3), 1),
      (List(2,4,6,8), 2),
      (List(4,8,16), 4),
      (List(2,3), 1)
    )

  it should s"${TaskId}: correctly find gcd for multiple numbers" in {
    forAll (gcdMultipleTestCases) { (input: List[Int], gcd: Int) =>
      Change.gcd(input) should equal (gcd)
    }
  }


}
