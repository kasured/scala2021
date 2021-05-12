package scala2021.erusak.task09

import scala2021.erusak.task09.CurrencyZone.Money

object Syntax {

  implicit class CurrencyOps[A: Currency](money1: Money[A]) {
    def +(money2: Money[A]): Money[A] = implicitly[Currency[A]].plus(money1, money2)
  }

  implicit class CrossCurrencyOps[A, B](moneyA: Money[A]) {
    def +(moneyB: Money[B])(implicit converter: Converter[B, A]): Money[A] = converter.convert(moneyB, moneyA)

    def to(currencyName: B)(implicit converter: Converter[A, B]): Money[B] = Money(moneyA.amount * converter.rate, currencyName)
  }

}
