package payroll

import com.mpi.kissing_company.entities.User
import org.springframework.data.jpa.repository.JpaRepository

internal interface EmployeeRepository : JpaRepository<User?, String?>