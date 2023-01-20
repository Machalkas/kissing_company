package com.mpi.kissing_company.controllers

import com.mpi.kissing_company.dto.AppFeedbacksDto
import com.mpi.kissing_company.dto.FeedbacksDto
import com.mpi.kissing_company.repositories.AppFeedbacksRepository
import com.mpi.kissing_company.repositories.FeedbacksRepository
import com.mpi.kissing_company.repositories.UserRepository
import com.mpi.kissing_company.utils.AppFeedbacksUtils
import com.mpi.kissing_company.utils.FeedbacksUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*


@RestController
internal class AppFeedbackController(private val repository: AppFeedbacksRepository,
                                     private val user_repository: UserRepository) {

    @Autowired
    private val feedbacksUtils = AppFeedbacksUtils()

    @GetMapping("/api/app_feedback")
    fun getFeedbacks(@PathVariable girl_id: Long?): List<AppFeedbacksDto?> {
        return repository.findAll().map { feedbacksUtils.mapToDto(it) }
    }


    @GetMapping("/api/app_feedback/avg")
    fun getAvg():Map<String, Double>  {
        val result_map = HashMap<String, Double>()
        val feedbacks_count = repository.count()
        val total_stars_sum = repository.sumStarsAll()
        result_map.put("stars_avg", total_stars_sum/feedbacks_count)
        return result_map
    }


    @PostMapping("/api/app_feedback")
    fun sendFeedback(auth: Authentication, @RequestBody newFeedback: AppFeedbacksDto) : AppFeedbacksDto {
        val user = user_repository.findByUsername(auth.name).get()
        newFeedback.setUsername(user.getUsername())
        val new_feedback = repository.save(feedbacksUtils.mapToEntity(newFeedback))
        return feedbacksUtils.mapToDto(new_feedback)
    }

}