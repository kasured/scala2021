package scala2021.erusak.task02

import scala.annotation.tailrec

/** Check [[BracketsSuite]] for more testcases */
object Brackets {
  /** Check if the brackets provided in the input are balanced
   *
   * {{{
   *   "f((2+x)*(3-y)==3)" is balanced
   *   "" is balanced
   *   "())(" is not balanced
   *   ")(" is not balanced
   * }}}
   *
   * @param input the raw input
   * @return true iff the balance of the brackets present
   *         otherwise false
   */
  def checkBracketsBalanced(input: List[Char]): Boolean = checkBrackets(input, 0)


  @tailrec
  private def checkBrackets(input: List[Char], open: Int): Boolean = input match {
    case _ if open < 0 => false
    case Nil if open == 0 => true
    case Nil => false
    case '(' :: tail => checkBrackets(tail, open + 1)
    case ')' :: tail => checkBrackets(tail, open - 1)
    case _ :: tail => checkBrackets(tail, open)
  }
}
