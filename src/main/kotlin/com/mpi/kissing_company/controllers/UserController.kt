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
