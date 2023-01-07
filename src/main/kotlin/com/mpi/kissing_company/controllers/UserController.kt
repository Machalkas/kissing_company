package com.mpi.kissing_company.controllers

import com.mpi.kissing_company.dto.UserDto
import com.mpi.kissing_company.dto.UserInfoDto
import com.mpi.kissing_company.entities.User
import com.mpi.kissing_company.exceptions.UserAlreadyExistException
import com.mpi.kissing_company.exceptions.UserNotFoundException
import com.mpi.kissing_company.repositories.RoleRepository
import com.mpi.kissing_company.repositories.UserRepository
import com.mpi.kissing_company.utils.UserUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*
import java.util.function.Function
import java.util.function.Supplier


@RestController
internal class UserController(private val repository: UserRepository,
                              private val role_repository: RoleRepository) {

    @Autowired
    private val userUtils = UserUtils()

    @GetMapping("/users")
    fun all(): List<UserInfoDto> {
        val all_users = repository.findAll()
        return all_users.map { userUtils.mapToDto(it) }
    }

    @GetMapping("/users/{username}")
    fun one(@PathVariable username: String): UserInfoDto? {
        val user_entity = repository.findByUsername(username).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "User not found") }
        return userUtils.mapToDto(user_entity)
    }

    @GetMapping("/users/me")
    fun myself(auth: Authentication): UserInfoDto {
        return userUtils.mapToDto(repository.findByUsername(auth.name).get())
    }

    @PutMapping("/users/{username}")
    fun updateUser(@RequestBody newUser: UserInfoDto, @PathVariable username: String): UserInfoDto {
        var user = repository.findByUsername(username).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "User not found") }
        user?.setsecond_name(newUser.getSecondName())
        user?.setfirst_name(newUser.getFirstName())
        user?.setRole(role_repository.findByName(newUser.getRoleName()))
        repository.save(user)
        return userUtils.mapToDto(user)

    }

    @DeleteMapping("/user/{username}")
    fun deleteUsername(@PathVariable username: String){
        if (repository.existsByUsername(username) == false){
            ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        }
        repository.deleteById(username)
    }
}
