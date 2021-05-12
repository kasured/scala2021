package scala2021.erusak.task09

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import scala2021.erusak.task09.CurrencyZone.{EUR, GBP, Money, USD}

class CurrencyZoneSuite extends AnyFlatSpec {

  private val TaskId = "Task 09 (Currency DSL)"

  import CurrencyZone.{MoneyWrapper, eurToUsd}
  import Syntax.CurrencyOps

  it should s"$TaskId Perform calculation for USD type currencies" in {
    val usdMoney: Money[USD.type] = 10(USD) + 20(USD)
    usdMoney shouldBe 30(USD)
  }

  it should s"$TaskId Perform calculation for EUR type currencies" in {
    val eurMoney: Money[EUR.type] = 10(EUR) + 20(EUR)
    eurMoney shouldBe 30(EUR)
  }

  it should s"$TaskId Perform calculation for cross type currencies" in {

    import Syntax.CrossCurrencyOps

    val resultInUsd: Money[USD.type] = 10(USD) + 20(EUR)

    resultInUsd shouldBe 32(USD)

    (resultInUsd to GBP) shouldBe 16(GBP)

  }

}
