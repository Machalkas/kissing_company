package com.mpi.kissing_company.repositories;

import com.mpi.kissing_company.entities.AppFeedbacks
import com.mpi.kissing_company.entities.Feedbacks
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

internal interface AppFeedbacksRepository : JpaRepository<AppFeedbacks?, String> {
    fun findById(id: Long?): AppFeedbacks
    @Query(value = "SELECT SUM(stars) FROM app_feedbacks", nativeQuery = true)
    fun sumStarsAll(): Double?
}