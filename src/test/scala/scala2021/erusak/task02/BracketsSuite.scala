package scala2021.erusak.task02

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import org.scalatest.prop.TableDrivenPropertyChecks._

class BracketsSuite extends AnyFlatSpec {

  private val TaskId = "Task 02"

  private val testCases =
    Table(
      ("input", "isBalanced"),
      ("", true),
      ("()", true),
      ("if((2+x)*(3-y)==3)", true),
      ("Я сказал ему (это еще (не) сделано). (Но он не послушал)", true),
      (")(", false),
      (";-)", false),
      ("())(", false),
      ("(((()()())))", true),
      ("(((()()()())", false),
      ("()()()()", true),
      ("()()()())", false),
      ("()()())()", false)
    )
  it should s"${TaskId}: correctly detect if the brackets are balanced in the input" in {
    forAll (testCases) { (input, isBalanced) =>
      Brackets.checkBracketsBalanced(input.toList) should equal (isBalanced)
    }
  }

}
