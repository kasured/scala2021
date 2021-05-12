package scala2021.erusak.task09

import scala2021.erusak.task09.CurrencyZone.Money

trait Converter[A, B] {
  def rate: Double
  def convert(money1: Money[A], money2: Money[B]): Money[B] =
    Money(money1.amount * rate + money2.amount, money2.currencyName)
}