package scala2021.erusak.task03

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.equal
import org.scalatest.matchers.should.Matchers._
import org.scalatest.prop.TableDrivenPropertyChecks._

class CompressionSuite extends AnyFlatSpec {
  private val TaskId = "Task 03"

  private val testCases =
    Table(
      ("aaaabccaadeeee", "4a1b2c2a1d4e"),
      ("a", "1a"),
      ("ab", "1a1b"),
      ("", ""),
      ("aaaa", "4a"),
      ("4aaa", "143a")
    )
  it should s"${TaskId}: correctly compress the string" in {
    forAll (testCases) { (input, compressedInput) =>
      Compression.compressStringAsString(input) should equal (compressedInput)
    }
  }

  private val testCasesSymbols =
    Table(
      ("input", "compressedInput"),
      (List(Symbol("a"), Symbol("a"), Symbol("a"), Symbol("a")), List((4, Symbol("a")))),
      (List(Symbol("a"), Symbol("a"), Symbol("a"), Symbol("a")), List((4, Symbol("a")))),
      (List(Symbol("a"), Symbol("a"), Symbol("b"), Symbol("b")), List((2, Symbol("a")), (2, Symbol("b")))),
      (List.empty, List.empty),
      (List(Symbol("a")), List((1, Symbol("a")))),
      (List(Symbol("a"), Symbol("b")), List((1, Symbol("a")), (1, Symbol("b"))))
    )

  it should s"${TaskId}: correctly compress the list of symbols" in {
    forAll (testCasesSymbols) { (input, compressedOutput)  =>
      Compression.encodeDirect(input) should equal (compressedOutput)
    }
  }
}
