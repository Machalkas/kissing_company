package com.mpi.kissing_company.dto


import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import lombok.Data

@Data
class LoginDto {
    private val usernameOrEmail: String? = null
    private val password: String? = null
}



//@PasswordMatches
//class UserDto {
//
//
//    @NotNull
//    @NotEmpty
//    private val firstName: String? = null
//
//    @NotNull
//    @NotEmpty
//    private val lastName: String? = null
//
//    @NotNull
//    @NotEmpty
//    private val password: String? = null
//    private val matchingPassword: String? = null
//
//    @NotNull
//    @NotEmpty
//    private val username: String? = null // standard getters and setters
//
//
//    fun getPassword(): String? {
//        return this.password
//    }
//
//    fun getMatchingPassword(): String? {
//        return this.matchingPassword
//    }
//
//    fun getUsername(): String? {
//        return  this.username
//    }
//}


