package com.mpi.kissing_company.repositories

import com.mpi.kissing_company.entities.Girl
import com.mpi.kissing_company.entities.ServiceHistory
import com.mpi.kissing_company.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime
import java.util.*

internal interface ServiceHistoryRepository: JpaRepository<ServiceHistory?, Long?> {
//    fun findById(id: Long?): Optional<ServiceHistory?>
    fun findByClient(user: User?): List<ServiceHistory?>
    fun findByGirl(girl: Girl?): List<ServiceHistory?>
    fun existsById(id: Long?): Boolean
    fun findFirstByGirlOrderByStartDtAsc(girl: Girl?): Optional<ServiceHistory?>
    fun findFirstByClientOrderByStartDtAsc(client: User?): Optional<ServiceHistory?>
    fun findByGirlOrderByStartDtAsc(girl: Girl?): List<ServiceHistory?>
    fun findByClientOrderByStartDtAsc(client: User?): List<ServiceHistory?>
    fun existsByGirlAndStartDtBetween(girl: Girl?, from: LocalDateTime, to: LocalDateTime): Boolean
}