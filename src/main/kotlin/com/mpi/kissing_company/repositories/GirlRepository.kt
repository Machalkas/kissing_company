package com.mpi.kissing_company.repositories

import com.mpi.kissing_company.entities.Girl
import com.mpi.kissing_company.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

internal interface GirlRepository : JpaRepository<Girl?, String>{
    fun findById(id: Long?): Optional<Girl?>
    fun findByUser(user: User): Optional<Girl?>
    fun existsByUser(user: User): Boolean
    fun existsById(id: Long): Boolean
    fun deleteById(id: Long)
}
