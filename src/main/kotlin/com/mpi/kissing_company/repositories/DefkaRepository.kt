package com.mpi.kissing_company.repositories

import com.mpi.kissing_company.entities.Defka
import com.mpi.kissing_company.entities.Role
import org.springframework.data.jpa.repository.JpaRepository

internal interface DefkaRepository : JpaRepository<Defka?, String> {
    override fun findAll(): MutableList<Defka?>;
}