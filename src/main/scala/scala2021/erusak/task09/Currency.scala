package scala2021.erusak.task09

import scala2021.erusak.task09.CurrencyZone.Money

import scala.annotation.implicitNotFound

@implicitNotFound("No instance of type class Currency found for type ${A}")
trait Currency[A] {
  def value(currency: Money[A]): Double

  def wrap(value: Double, currencyName: A): Money[A]

  def plus(curr1: Money[A], curr2: Money[A]): Money[A] = wrap(value(curr1) + value(curr2), curr1.currencyName)
}
