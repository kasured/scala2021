package scala2021.erusak.task05

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.{convertToAnyShouldWrapper, equal}

import scala.concurrent.ExecutionContext

class AccessSuite extends AnyFlatSpec {

  private val TaskId = "Task 05 (Access)"

  val employees = List(
    Employee(1, "Steve", 1),
    Employee(3, "Mark", 1),
    Employee(4, "Jane", 1),
    Employee(7, "Samuel", 2),
    Employee(10, "Igor", 2),
    Employee(11, "Naveen", 4),
    Employee(12, "Christy", 5),
    Employee(15, "Megan", 3)
  )
  val departments = List(
    Department(1, "Marketing"),
    Department(2, "Sales"),
    Department(3, "Research"),
    Department(4, "IT"),
  )
  val managers = List(
    Manager("Marketing", 1),
    Manager("Sales", 10),
    Manager("IT", 14),
  )

  private val searchOps = SearchOps(employees, departments, managers)

  it should s"${TaskId} correctly find the managers of departments by its employee" in {
    searchOps.findManagerName("John") should equal (None)
    searchOps.findManagerName("Steve") should equal (Some("Steve"))
    searchOps.findManagerName("Mark") should equal (Some("Steve"))
    searchOps.findManagerName("Igor") should equal (Some("Igor"))
    searchOps.findManagerName("Christy") should equal (None)
    searchOps.findManagerName("Naveen") should equal (None)
    searchOps.findManagerName("Megan") should equal (None)
  }

  it should s"${TaskId} correctly handle finding the managers of departments by its employee" in {
    searchOps.findManagerNameOrError("John") should equal (Left("Employee 'John' Not Found"))
    searchOps.findManagerNameOrError("Steve") should equal (Right("Steve"))
    searchOps.findManagerNameOrError("Mark") should equal (Right("Steve"))
    searchOps.findManagerNameOrError("Igor") should equal (Right("Igor"))
    searchOps.findManagerNameOrError("Christy") should equal (Left("Department with id '5' Not Found"))
    searchOps.findManagerNameOrError("Naveen") should equal (Left("Employee with id '14' Not Found"))
    searchOps.findManagerNameOrError("Megan") should equal (Left("Manager for department name 'Research' Not Found"))
  }

  it should s"${TaskId} correctly handle finding the managers of departments by its employee (async)" in {
    implicit val executionContext: ExecutionContext = ExecutionContext.Implicits.global
    searchOps.findManagerNameOrErrorAsync("John") map { result => assert(result == Left("Employee 'John' Not Found"))}
    searchOps.findManagerNameOrErrorAsync("Steve") map { result => assert(result == Right("Steve"))}
  }

  it should s"${TaskId} correctly fetch information about employees" in {
    searchOps.findEmployeeManagers should equal (List(
      Info("Steve","Marketing","Steve"),
      Info("Mark","Marketing","Steve"),
      Info("Jane","Marketing","Steve"),
      Info("Samuel","Sales","Igor"),
      Info("Igor","Sales","Igor"),
      Info("Naveen","IT","Not Found"),
      Info("Christy","Not Found","Not Found"),
      Info("Megan","Research","Not Found")
    ))
  }


}
