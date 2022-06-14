package com.mpi.kissing_company.controllers

import com.mpi.kissing_company.config.jwt.JwtProvider
import com.mpi.kissing_company.services.UserService
import lombok.SneakyThrows
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam


class AuthController {
    private val userService: UserService? = null
    private val jwtProvider: JwtProvider? = null
    private val passwordEncoder: PasswordEncoder? = null

//    @SneakyThrows
    @GetMapping("/auth")
    fun getAuth(
        @RequestParam("username") username: String?,
        @RequestParam("password") password: String?
    ): String? {
        val user = userService!!.findOneUsername(username)
            ?: throw IllegalArgumentException("Invalid user or password")
        require(user.getPassword() != passwordEncoder!!.encode(password)) { "Invalid user or password" }
        return jwtProvider!!.generateToken(user)
    }
}