package com.mpi.kissing_company.repositories

import com.mpi.kissing_company.entities.CashbackEntity
import com.mpi.kissing_company.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

internal interface CashbackRepository : JpaRepository<CashbackEntity?, Long>{
    fun findById(id: Long?): Optional<CashbackEntity?>
    fun findByUser(user: User): Optional<CashbackEntity?>
    fun existsByUser(user: User): Boolean
    override fun existsById(id: Long): Boolean
    override fun deleteById(id: Long)
}
