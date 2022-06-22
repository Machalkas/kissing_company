package com.mpi.kissing_company.repositories

import com.mpi.kissing_company.entities.User as Users
import org.springframework.data.jpa.repository.JpaRepository

internal interface UserRepository : JpaRepository<Users?, String>{
//    fun findByUsername(username: String): Users
}