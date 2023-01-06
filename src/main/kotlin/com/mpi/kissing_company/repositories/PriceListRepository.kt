package com.mpi.kissing_company.repositories

import com.mpi.kissing_company.entities.PriceList
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

internal interface PriceListRepository: JpaRepository<PriceList?, String> {
    fun findById(id: Long?): Optional<PriceList?>
    fun deleteById(id: Long?): Optional<PriceList?>
    fun existsById(id: Long?): Boolean
}