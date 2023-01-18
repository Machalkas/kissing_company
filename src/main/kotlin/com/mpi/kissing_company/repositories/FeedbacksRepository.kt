package com.mpi.kissing_company.repositories;

import com.mpi.kissing_company.entities.Feedbacks
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

internal interface FeedbacksRepository : JpaRepository<Feedbacks?, String> {
    fun findById(id: Long?): Feedbacks
    fun findByGirlIdOrderByCreateAtDesc(id: Long?): List<Feedbacks?>
    @Query(value = "SELECT SUM(stars) FROM feedbacks", nativeQuery = true)
    fun sumStarsByGirlId(id: Long?): Double
    fun countByGirlId(id: Long?): Int
}