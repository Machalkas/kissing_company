package com.mpi.kissing_company.controllers

import com.mpi.kissing_company.entities.User
import com.mpi.kissing_company.exceptions.UserNotFoundException
import com.mpi.kissing_company.repositories.UserRepository
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.function.Function
import java.util.function.Supplier


@RestController
internal class UserController(private val repository: UserRepository) {

    @GetMapping("/users")
    fun all(): List<User?> {
        return repository.findAll()
    }

    @PostMapping("/users")
    fun newUser(@RequestBody newUser: User): User{
        return repository.save(newUser)
    }

    @GetMapping("/users/{username}")
    fun one(@PathVariable username: String): User? {
        return repository.findById(username)
            .orElseThrow(Supplier<RuntimeException> { UserNotFoundException(username) })
    }

    @PutMapping("/users/{username}")
    fun replaceUser(@RequestBody newUser: User, @PathVariable username: String): Optional<User>? {
        return repository.findById(username)
            .map<User>(Function { user: User ->
                user.first_name = newUser.first_name
                user.second_name = newUser.second_name
                user.role = newUser.role
                repository.save(user)} as (User?) -> User)
    }

    @DeleteMapping("/user/{username}")
    fun deleteUsername(@PathVariable username: String){
        repository.deleteById(username)
    }
}
//@RestController
//internal class EmployeeController(private val repository: UserRepository) {
//    // Aggregate root
//    // tag::get-aggregate-root[]
//    @GetMapping("/employees")
//    fun all(): List<User?> {
//        return repository.findAll()
//    }
//
//    // end::get-aggregate-root[]
//    @PostMapping("/employees")
//    fun newEmployee(@RequestBody newEmployee: Employee): Employee {
//        return repository.save(newEmployee)
//    }
//
//    // Single item
//    @GetMapping("/employees/{id}")
//    fun one(@PathVariable id: Long): Employee {
//        return repository.findById(id)
//            .orElseThrow(Supplier<RuntimeException> { EmployeeNotFoundException(id) })
//    }
//
//    @PutMapping("/employees/{id}")
//    fun replaceEmployee(@RequestBody newEmployee: Employee, @PathVariable id: Long): Employee {
//        return repository.findById(id)
//            .map<User>(Function { employee: User ->
//                employee.setName(newEmployee.getName())
//                employee.role = newEmployee.getRole()
//                repository.save(employee)
//            })
//            .orElseGet {
//                newEmployee.setId(id)
//                repository.save(newEmployee)
//            }
//    }
//
//    @DeleteMapping("/employees/{id}")
//    fun deleteEmployee(@PathVariable id: Long) {
//        repository.deleteById(id)
//    }
//}