package scala2021.erusak.task07

import com.typesafe.scalalogging.StrictLogging
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

import scala.util.Success
import org.scalatest.TryValues._
import org.scalatest.matchers.must.Matchers.have
import scala2021.erusak.task07.ResourceApi.Connection

import java.io.File

class ControlStructureSuite extends AnyFlatSpec with StrictLogging {
  private val TaskId = "Task 07 (Control Structure)"

  private val connRun: Connection => Unit = conn => conn.run()
  private val fileExists: File => Boolean = file => file.exists()

  it should s"$TaskId properly handle Connection resource" in {

    ResourceApi.withConnection(port = 9000) {
      connRun
    } shouldBe Success(())

    ResourceApi.withConnection(port = 9001) {
      connRun
    }.failure.exception should have message "Issue during connection init"

    ResourceApi.withConnection(port = 9002) {
      connRun
    }.failure.exception should have message "Issue during connection run"

    ResourceApi.withConnection(port = 9003) {
      connRun
    }.failure.exception should have message "Issue during connection close"

  }

  it should s"$TaskId properly handle File resource" in {

    ResourceApi.withFile(path = "/tmp/") {
      fileExists
    } shouldBe Success(true)

    ResourceApi.withFile(path = "/tmp/sdfsdfsdfs") {
      fileExists
    } shouldBe Success(false)

  }

  it should s"$TaskId properly handle FileInputStream resource" in {

    import java.io.File
    val tempFile = File.createTempFile("scalatest-", "-exadel")

    ResourceApi.withFileInputStream(path = tempFile.getPath) {
      fis => {
        val bytes = fis.available()
        logger.info(s"Count bytes are $bytes")
        bytes
      }

    } shouldBe Success(0)

    tempFile.deleteOnExit()

  }

}
