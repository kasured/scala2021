package scala2021.erusak.task09

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import scala2021.erusak.task09.CurrencyZone.{EUR, GBP, Money, USD}

class CurrencyZoneSuite extends AnyFlatSpec {

  private val TaskId = "Task 09 (Currency DSL)"

  import CurrencyZone.MoneyWrapper
  import Syntax.CrossCurrencyOps

  it should s"$TaskId Perform calculation for USD type currencies" in {
    val usdMoney: Money[USD.type] = 10(USD) + 20(USD)
    usdMoney shouldBe 30(USD)
  }

  it should s"$TaskId Perform calculation for EUR type currencies" in {
    val eurMoney: Money[EUR.type] = 10(EUR) + 20(EUR)
    eurMoney shouldBe 30(EUR)
  }

  it should s"$TaskId Perform calculation for cross type currencies" in {

    val resultInUsd: Money[USD.type] = 10(USD) + 20(EUR)

    resultInUsd shouldBe 32(USD)

    (resultInUsd to GBP) shouldBe 16(GBP)

  }

  it should s"$TaskId Perform calculation for cross type currencies (advanced)" in {

    val eurMoney: Money[EUR.type] = 10(EUR) + 20(EUR)

    val usdMoney = eurMoney to USD
    val gbpMoney = eurMoney to GBP

    usdMoney shouldBe 33(USD)
    gbpMoney shouldBe 24(GBP)

  }



}
