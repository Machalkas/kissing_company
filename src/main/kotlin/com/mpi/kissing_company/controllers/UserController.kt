package com.mpi.kissing_company.controllers

import com.mpi.kissing_company.dto.UserDto
import com.mpi.kissing_company.entities.User
import com.mpi.kissing_company.exceptions.UserAlreadyExistException
import com.mpi.kissing_company.exceptions.UserNotFoundException
import com.mpi.kissing_company.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
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

//    @PostMapping("/users")
//    fun newUser(@RequestBody newUser: User): User{
//        return repository.save(newUser)
//    }

    @GetMapping("/users/{username}")
    fun one(@PathVariable username: String): User? {
        return repository.findById(username)
            .orElseThrow(Supplier<RuntimeException> { UserNotFoundException(username) })
    }

    @PutMapping("/users/{username}")
    fun updateUser(@RequestBody newUser: User, @PathVariable username: String): Optional<User>? {
        return repository.findById(username)
            .map<User>(Function { user: User ->
                user.setfirst_name(newUser.getfirst_name())
                user.setsecond_name(newUser.getsecond_name())
                user.setRole(newUser.getRole())
                repository.save(user)} as (User?) -> User)
    }

    @DeleteMapping("/user/{username}")
    fun deleteUsername(@PathVariable username: String){
        repository.deleteById(username)
    }
}
