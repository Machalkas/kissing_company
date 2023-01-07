package com.mpi.kissing_company.repositories

import com.mpi.kissing_company.entities.Role
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
import com.mpi.kissing_company.entities.User as Users

internal interface UserRepository : JpaRepository<Users?, String>{
    fun findByUsername(username: String?): Optional<Users>
    fun existsByUsername(username: String?): Boolean?
}