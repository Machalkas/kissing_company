package com.mpi.kissing_company.repositories

import com.mpi.kissing_company.entities.Role
import org.springframework.data.jpa.repository.JpaRepository
import com.mpi.kissing_company.entities.User as Users

internal interface UserRepository : JpaRepository<Users?, String>{
    fun findByUsername(username: String?): Users
    fun existsByUsername(username: String?): Boolean?
}

interface UserDetailProjection {  // FIXME: return password hash
    val username: String
    val first_name: String
    val second_name: String
    val role: Role
}