package com.mpi.kissing_company.repositories

import org.springframework.data.jpa.repository.JpaRepository
import com.mpi.kissing_company.entities.Users

interface UserRepository : JpaRepository<Users?, Long?> {
    fun findByUsername(username: String?): Users?
    fun findByRole(role: String?): List<Users?>?
}
