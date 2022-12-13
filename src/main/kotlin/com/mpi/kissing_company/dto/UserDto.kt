package com.mpi.kissing_company.dto

import com.mpi.kissing_company.validators.PasswordMatches
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

import lombok.Data

@Data
class LoginDto {
    private val username: String? = null
    private val password: String? = null

    fun getPassword(): String? {
        return this.password
    }

    fun getUsername(): String? {
        return  this.username
    }
}

@Data
class SignUpDto {
    private val first_name: String? = null
    private val second_name: String? = null
    @NotNull
    @NotEmpty
    private val username: String? = null
    @NotNull
    @NotEmpty
    private val password: String? = null
    private var role: String? = null

    init {
        this.role = this.role?.uppercase()
    }

    fun getPassword(): String? {
        return this.password
    }

    fun getUsername(): String? {
        return  this.username
    }

    fun getSecond_name(): String? {
        return  this.second_name
    }

    fun getFirst_name(): String? {
        return  this.first_name
    }

    fun getRole(): String? {
        return this.role
    }

}

@PasswordMatches
class UserDto {


    @NotNull
    @NotEmpty
    private val firstName: String? = null

    @NotNull
    @NotEmpty
    private val lastName: String? = null

    @NotNull
    @NotEmpty
    private val password: String? = null
    private val matchingPassword: String? = null

    @NotNull
    @NotEmpty
    private val username: String? = null // standard getters and setters


    fun getPassword(): String? {
        return this.password
    }

    fun getMatchingPassword(): String? {
        return this.matchingPassword
    }

    fun getUsername(): String? {
        return  this.username
    }
}



