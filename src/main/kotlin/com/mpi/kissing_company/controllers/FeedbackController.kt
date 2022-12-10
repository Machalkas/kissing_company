package com.mpi.kissing_company.controllers

import com.mpi.kissing_company.entities.Feedbacks
import com.mpi.kissing_company.entities.User
import com.mpi.kissing_company.repositories.FeedbacksRepository
import org.springframework.data.jpa.domain.Specification
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

@RestController
internal class FeedbackController(private val repository: FeedbacksRepository) {

    @GetMapping("/feedback/{girl_id}")
    fun GetFeedbacks(@PathVariable girl_id: Long?): List<Feedbacks?> {

//        val spec: Specification<Feedbacks> = object : Specification<Feedbacks?>() {
//            fun toPredicate(root: Root<Feedbacks?>, query: CriteriaQuery<*>?, cb: CriteriaBuilder): Predicate? {
//                return cb.equal(root.get("girl_id"), girl_id)
//            }
//        }
        return repository.findAllByGirlId(girl_id)
    }


    @PostMapping("/feedback")
    fun SendFeedback(@RequestBody Newfeedback: Feedbacks) : Feedbacks{
        return repository.save(Newfeedback)
    }


}