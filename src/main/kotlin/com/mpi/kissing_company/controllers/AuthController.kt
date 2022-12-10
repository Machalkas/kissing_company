package com.mpi.kissing_company.controllers

import com.mpi.kissing_company.dto.LoginDto
import com.mpi.kissing_company.dto.SignUpDto
import com.mpi.kissing_company.entities.Role
import com.mpi.kissing_company.entities.User
import com.mpi.kissing_company.repositories.RoleRepository
import com.mpi.kissing_company.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@RestController
@RequestMapping("/api/auth")
class AuthController {
    @Autowired
    private val authenticationManager: AuthenticationManager? = null

    @Autowired
    private val userRepository: UserRepository? = null

    @Autowired
    private val roleRepository: RoleRepository? = null

    @Autowired
    private val bCryptPasswordEncoder: BCryptPasswordEncoder? = null

    @PostMapping("/login")
    fun authenticateUser(@RequestBody loginDto: LoginDto): ResponseEntity<String> {
        println("login!")
        val authentication = authenticationManager!!.authenticate(
            UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword()
            )
        )
        println(authentication)
        SecurityContextHolder.getContext().authentication = authentication
        return ResponseEntity("User signed-in successfully!", HttpStatus.OK)
    }

    @PostMapping("/registration")
    fun registerUser(@RequestBody signUpDto: SignUpDto): ResponseEntity<*> {

        // add check for username exists in a DB
        if (userRepository?.existsByUsername(signUpDto.getUsername()) == true) {
            return ResponseEntity("Username is already taken!", HttpStatus.BAD_REQUEST)
        }


        // create user object
        val user = User()
        user.setFName(signUpDto.getFName())
        user.setSName(signUpDto.getSName())
        user.setUsername(signUpDto.getUsername())
        user.setPassword(bCryptPasswordEncoder?.encode(signUpDto.getPassword()))
        val roles: Role? = roleRepository?.findByName("ROLE_ADMIN")
        user.setRole(roles)
        userRepository?.save(user)
        return ResponseEntity("User registered successfully", HttpStatus.OK)
    }
}