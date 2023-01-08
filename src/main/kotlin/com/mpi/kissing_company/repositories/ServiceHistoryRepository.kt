package com.mpi.kissing_company.repositories

import com.mpi.kissing_company.entities.PriceList
import com.mpi.kissing_company.entities.ServiceHistory
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

internal interface ServiceHistoryRepository: JpaRepository<ServiceHistory?, String> {
    fun findById(id: Long?): Optional<ServiceHistory?>
    fun findByUser
    fun existsById(id: Long?): Boolean
}