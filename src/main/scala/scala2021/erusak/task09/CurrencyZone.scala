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

  implicit val eurToUsd: Converter[EUR.type, USD.type] = new Converter[EUR.type, USD.type] {
    override def rate: Double = 1.1
  }

  implicit val eurToGbp: Converter[EUR.type, GBP.type] = new Converter[EUR.type, GBP.type] {
    override def rate: Double = 0.8
  }

  implicit val eurToEur: Converter[EUR.type, EUR.type] = new Converter[EUR.type, EUR.type] {
    override def rate: Double = 1.0
  }

  implicit val usdToUsd: Converter[USD.type, USD.type] = new Converter[USD.type, USD.type] {
    override def rate: Double = 1.0
  }

  implicit val usdToGbp: Converter[USD.type, GBP.type] = new Converter[USD.type, GBP.type] {
    override def rate: Double = 0.5
  }

}
