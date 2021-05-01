package scala2021.erusak

import scala.concurrent.Future

package task05 {

  case class Employee(id: Int, name: String, departmentId: Int)
  case class Department(id: Int, name: String)
  case class Manager(department: String, employeeId: Int)
  case class Info(employee: String, department: String, manager: String)

  trait SearchOps {
    def findManagerName(employee: String): Option[String]
    def findManagerNameOrError(employee: String): Either[String, String]
    def findManagerNameOrErrorAsync(employee: String): Future[Either[String, String]]
    def findEmployeeManagers: List[Info]
  }

  object SearchOps {

    import scala.concurrent.ExecutionContext.Implicits.global

    def apply(employees: List[Employee], departments: List[Department], managers: List[Manager]): SearchOps =
      new LocalSearch(employees, departments, managers)

    private val NotFound = "Not Found"

    private def overwriteReducer[T]: (T, T) => T = (former, later) => former

    private final class LocalSearch(
      val employees: List[Employee],
      val departments: List[Department],
      val managers: List[Manager]
    ) extends SearchOps {

      override def findManagerName(employee: String): Option[String] =
        findManagerNameOrError(employee).toOption

      override def findManagerNameOrError(employee: String): Either[String, String] =
        findInfo(employee).map(_.manager)

      override def findManagerNameOrErrorAsync(employee: String): Future[Either[String, String]] = Future {
        findManagerNameOrError(employee)
      }

      override def findEmployeeManagers: List[Info] = employees.map(employee => {
        val departamentName = findDepartmentByEmployee(employee.name)
        val managerName = departamentName.flatMap(findManagerByDepartmentName)
        Info(
          employee.name,
          departamentName.getOrElse(NotFound),
          managerName.getOrElse(NotFound)
        )
      })

      private def findDepartmentByEmployee(employee: String): Either[String, String] =
        for {
          employeeEntity <- employeeByName.get(employee).toRight(s"Employee '$employee' Not Found")
          department <- departmentById.get(employeeEntity.departmentId).toRight(s"Department with id '${employeeEntity.departmentId}' Not Found")
        } yield department.name

      private def findManagerByDepartmentName(department: String): Either[String, String] =
        for {
          manager <- managerByDepartmentName.get(department).toRight(s"Manager for department name '$department' Not Found")
          managerAsEmployee <- employeeById.get(manager.employeeId).toRight(s"Employee with id '${manager.employeeId}' Not Found")
        } yield managerAsEmployee.name

      private def findInfo(employee: String): Either[String, Info] =
        for {
          departmentName <- findDepartmentByEmployee(employee)
          managerAsEmployeeName <- findManagerByDepartmentName(departmentName)
        } yield Info(employee, departmentName, managerAsEmployeeName)

      // we might want to some invariants validation logic here and fail fast
      validate(employees, departments, managers)

      private val employeeById = employees.groupMapReduce(_.id)(identity)(overwriteReducer)

      private val employeeByName = employees.groupMapReduce(_.name)(identity)(overwriteReducer)

      private val departmentById = departments.groupMapReduce(_.id)(identity)(overwriteReducer)

      private val managerByDepartmentName = managers.groupMapReduce(_.department)(identity)(overwriteReducer)

    }

    private def validate(employees: List[Employee], departments: List[Department], managers: List[Manager]): Unit = ()

  }

}
