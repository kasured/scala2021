package scala2021.erusak.task03

import scala.annotation.tailrec

/** Check [[CompressionSuite]] for more testcases */
object Compression {

  /** Calculates the number of chars in consecutive intervals
   * {{{
   *   "aaabbbbccccc"
   *   "3a4b5c"
   * }}}
   * {{{
   *   ""
   *   ""
   * }}}
   * @param input input string
   * @return string representing number the length on the consecutive interval and char representing it
   */
  def compressStringAsString(input: String): String = input match {
    case "" => ""
    case _ =>
      val inputAsSymbols = input.map(char => Symbol(char.toString)).toList
      encodeDirect(inputAsSymbols).map{ case (c, s) => s"$c${s.name}" }.mkString
  }

  def encodeDirect(input: List[Symbol]): List[(Int, Symbol)] = input match {
    case Nil => Nil
    case head :: tail => encodeDirect(tail, (1, head), List.empty).reverse
  }


  @tailrec
  private def encodeDirect(input: List[Symbol], prev: (Int, Symbol), result: List[(Int, Symbol)]): List[(Int, Symbol)] =
    (input, prev) match {
      case (Nil, _) => prev :: result
      case (head :: tail, (prevCount, prevSymbol)) if head != prevSymbol => encodeDirect(tail, (1, head), prev :: result)
      case (head :: tail, (prevCount, prevSymbol)) => encodeDirect(tail, (prevCount + 1, prevSymbol), result)
  }

}
