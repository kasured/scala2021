package scala2021.erusak.task01

import com.typesafe.scalalogging.StrictLogging

/**
 * See tests for more use case details
 */
object Domains extends StrictLogging {

  type DomainsWithImpression = Map[String, Long]

  /**
   * Constructs data structure containing the number of impressions that were recorded in each domain and each domain below it
   *
   * @param array comma separated array of parent domains with visited count in the form "4,google.com"
   * @return map of all the domains and subdomains with the visited count
   */
  def visitedDomainsWithCount(array: Array[String]): DomainsWithImpression = {

    array.map(_.split(","))
      // explode the visited domains and subdomains with the same count
      .flatMap(domainsWithCountExplode)
      // key as subdomain string
      // value as count
      // sum by the same key
      .groupMapReduce(keyExtractor)(valueExtractor)(_ + _)

  }

  /**
   * See [[visitedDomainsWithCount]] for details
   *
   * @param array [[visitedDomainsWithCount(array)]]
   * @return result as a String
   */
  def visitedDomainsWithCountPretty(array: Array[String]): String =
    visitedDomainsWithCount(array)
      .map { case (domain, count) => s"$count $domain" }
      .mkString("\n")

  /**
   *
   * @param domain {{{"x.y.com"}}}
   * @return {{{Seq("x.y.com", "y.com", "com")}}}
   */
  private def domainsToSubDomains(domain: String): Seq[String] = {
    val parts = domain.split("\\.")
    val subdomains = parts.tails.toSeq.dropRight(1).map(_.mkString("."))
    subdomains
  }

  /**
   *
   * @param domainWithCount {{{
   *                        ["4", "x.y.com"]
   *                        }}}
   * @return {{{
   *         [[4, "x.y.com"],[4, "y.com"],[4, "com"]]
   *         }}}
   */
  private def domainsWithCountExplode(domainWithCount: Array[String]): Array[(Long, String)] = {
    val (count, domain) = (domainWithCount(0).toLong, domainWithCount(1))

    val subDomains = domainsToSubDomains(domain)

    // add all subdomains the same visited count
    Array(count).padTo(subDomains.length, count).zip(subDomains)

  }

  private def keyExtractor(countWithDomain: (Long, String)): String = {
    val (_, domain) = countWithDomain
    domain
  }

  private def valueExtractor(countWithDomain: (Long, String)): Long = {
    val (count, _) = countWithDomain
    count
  }

}
