package com.mpi.kissing_company.repositories

import com.mpi.kissing_company.entities.Girl
import org.springframework.data.jpa.repository.JpaRepository

internal interface GirlRepository : JpaRepository<Girl?, String>{
    fun findById(id: Long): Girl
}
