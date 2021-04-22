package scala2021.erusak.task01

import com.typesafe.scalalogging.StrictLogging
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.convertToAnyMustWrapper
import org.scalatest.matchers.should
import scala2021.erusak.task01.Domains.DomainsWithImpression
import scala2021.erusak.task01.DomainsSuite.{domainsAsStringsToExpectedMap, rawInputToDomains}

class DomainsSuite extends AnyFlatSpec with should.Matchers with StrictLogging {

  private val TaskId = "Task 01"

  private val regularInput = Array(
    "900,google.com",
    "60,mail.yahoo.com",
    "10,mobile.sports.yahoo.com",
    "40,sports.yahoo.com",
    "10,stackoverflow.com",
    "2,en.wikipedia.org",
    "1,es.wikipedia.org",
    "1,mobile.sports"
  )

  private val regularExpectedOutputAsString =
    """1020 com
      |900 google.com
      |110 yahoo.com
      |60 mail.yahoo.com
      |10 mobile.sports.yahoo.com
      |50 sports.yahoo.com
      |10 stackoverflow.com
      |3 org
      |3 wikipedia.org
      |2 en.wikipedia.org
      |1 es.wikipedia.org
      |1 mobile.sports
      |1 sports
      |""".stripMargin

  it should s"${TaskId}: parse the input and calculate the count of visited domains" in {

    val calculatedResult = Domains.visitedDomainsWithCount(regularInput)

    val expectedResult = domainsAsStringsToExpectedMap(rawInputToDomains(regularExpectedOutputAsString))

    calculatedResult.toSet.diff(expectedResult.toSet) mustBe Set.empty
    expectedResult.toSet.diff(calculatedResult.toSet) mustBe Set.empty

  }

  // testing of internal Map-to-Map comparison logic
  it should s"${TaskId}: properly parse the expected output to Map" in {

    val splitArray = rawInputToDomains(regularExpectedOutputAsString)

    val domainsWithCounts = domainsAsStringsToExpectedMap(splitArray)

    val reverse = domainsWithCounts.map { case (domain, count) => s"$count $domain"}

    // checks if the reverse operation is the same giving the internal testing verification
    splitArray.toSet.subsetOf(reverse.toSet) mustBe true
    reverse.toSet.subsetOf(splitArray.toSet) mustBe true

  }

}

object DomainsSuite {

  private def rawInputToDomains(rawInput: String): Array[String] = rawInput.split("\n")

  private def domainsAsStringsToExpectedMap(rawInput: Array[String]): DomainsWithImpression = {
    rawInput
      //split the line once
      .map(_.split(" "))
      //create reversed index allowing for multiple expected domains
      .groupMapReduce(countAndDomain => countAndDomain(1))(countAndDomain => countAndDomain(0).toLong)(_ + _)
  }
}
