package scala2021.erusak.task09

object CurrencyZone {

  sealed trait CurrencyName
  final case object USD extends CurrencyName
  final case object EUR extends CurrencyName
  final case object GBP extends CurrencyName

  implicit class MoneyWrapper(amount: Double) {
    def apply[A](currencyName: A): Money[A] = Money(amount, currencyName)
  }

  final case class Money[A](amount: Double, currencyName: A)

  implicit val usdImpl: Currency[USD.type] = new Currency[USD.type] {
    override def value(currency: Money[USD.type]): Double = currency.amount
    override def wrap(value: Double, currencyName: USD.type): Money[USD.type] = Money(value, currencyName)
  }

  implicit val eurImpl: Currency[EUR.type] = new Currency[EUR.type] {
    override def value(currency: Money[EUR.type]): Double = currency.amount
    override def wrap(value: Double, currencyName: EUR.type): Money[EUR.type] = Money(value, currencyName)
  }

  implicit val gbpImpl: Currency[GBP.type] = new Currency[GBP.type] {
    override def value(currency: Money[GBP.type]): Double = currency.amount
    override def wrap(value: Double, currencyName: GBP.type): Money[GBP.type] = Money(value, currencyName)
  }

  implicit val eurToUsd: Converter[EUR.type, USD.type] = new Converter[EUR.type, USD.type] {
    override def rate: Double = 1.1
  }

  implicit val usdToGbp: Converter[USD.type, GBP.type] = new Converter[USD.type, GBP.type] {
    override def rate: Double = 0.5
  }

}
