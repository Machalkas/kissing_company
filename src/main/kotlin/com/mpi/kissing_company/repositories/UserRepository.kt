package com.mpi.kissing_company.repositories

import org.springframework.data.jpa.repository.JpaRepository
import com.mpi.kissing_company.entities.User as Users

internal interface UserRepository : JpaRepository<Users?, String>{
    fun findByUsername(username: String?): Users
    fun existsByUsername(username: String?): Boolean?
}