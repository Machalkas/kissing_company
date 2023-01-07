package com.mpi.kissing_company.dto

import com.mpi.kissing_company.validators.PasswordMatches
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

import lombok.Data
import java.time.LocalDateTime

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


class UserInfoDto {

    private var firstName: String? = null
    private var secondName: String? = null
    private var username: String? = null
    private var roleName: String? = null
    private var create_at: LocalDateTime? = null
    private var update_at: LocalDateTime? = null

    constructor(username: String?, firstName: String?, secondName: String?, roleName: String?) {
        this.firstName = firstName
        this.secondName = secondName
        this.roleName = roleName
        this.username = username
    }

    fun getUsername(): String? {
        return this.username
    }

    fun getFirstName(): String? {
        return  this.firstName
    }

    fun getSecondName(): String? {
        return this.secondName
    }

    fun getRoleName(): String? {
        return this.roleName
    }

    fun setUsername(username: String?){
        this.username = username
    }

    fun setFirstName(first_name: String?){
        this.firstName = first_name
    }

    fun setSecondName(last_name: String?){
        this.secondName = last_name
    }

    fun setRoleName(role_name: String?){
        this.roleName = role_name
    }
}


