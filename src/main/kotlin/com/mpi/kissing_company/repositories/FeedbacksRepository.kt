package com.mpi.kissing_company.repositories;

import com.mpi.kissing_company.entities.Feedbacks
import com.mpi.kissing_company.entities.Girl
import org.springframework.data.jpa.repository.JpaRepository

internal interface FeedbacksRepository : JpaRepository<Feedbacks?, Long> {
    fun findById(id: Long?): Feedbacks

    fun findAllByGirlId(id: Long?): List<Feedbacks?>
}