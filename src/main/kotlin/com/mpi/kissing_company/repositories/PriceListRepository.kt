package com.mpi.kissing_company.repositories

import com.mpi.kissing_company.entities.PriceList
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PriceListRepository: JpaRepository<PriceList?, String> {
    fun findById(id: Long?): Optional<PriceList?>
}