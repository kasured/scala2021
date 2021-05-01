package scala2021.erusak.task04

import scala.annotation.tailrec

object Change {

  def isChangePossible(change: Int, coins: List[Int]): Boolean =
    isChangePossibleRec(change, coins.sorted.reverse)

  //@tailrec
  private def isChangePossibleRec(change: Int, coins: List[Int]): Boolean = coins match {
    case _ if change < 0 => false
    case Nil => change == 0
    case head :: tail => isChangePossibleRec(change - head, coins) || isChangePossibleRec(change, tail)
    case _ => false
  }

  @tailrec
  def gcd(a: Int, b: Int): Int = {
    if (a == 0) b else gcd(b % a, a)
  }

  def gcd(list: List[Int]): Int = list.reduce(gcd)
}
