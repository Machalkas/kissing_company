package com.mpi.kissing_company.controllers

import com.mpi.kissing_company.entities.UserSave
import com.mpi.kissing_company.entities.Users
import com.mpi.kissing_company.services.UserService
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*



@RestController // @Controller + @ResponseBody over each method
@RequestMapping("/user")
@RequiredArgsConstructor
class UserController {
    private val userService: UserService? = null

    @get:GetMapping
    val all: List<Users?>
        get() = userService!!.findAllAndSort()

    @GetMapping("/role")
    fun getUserByRole(@RequestParam("role") role: String?): List<Users?>? {
        return userService?.findByRole(role)
    }

    @GetMapping("/id")
    fun getUser(@RequestParam("id") id: String): Users? {
        return userService?.findOneUser(id)
    }

    @PostMapping("/save")
    fun saveUser(@RequestBody user: UserSave): ResponseEntity<Users> {
        val user1: Users? = userService?.saveUser(user)
        return if (user1 != null) {
            ResponseEntity.status(HttpStatus.CREATED).body<Users>(user1)
        } else ResponseEntity.status(HttpStatus.BAD_REQUEST).build<Users>()
    }

    @GetMapping("/user_login")
    fun getOne(@RequestParam("username") username: String?): Users? {
        return userService?.findOneUsername(username)
    }
}