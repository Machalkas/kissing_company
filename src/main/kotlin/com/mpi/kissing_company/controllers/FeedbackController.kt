package com.mpi.kissing_company.controllers

import com.mpi.kissing_company.dto.FeedbacksDto
import com.mpi.kissing_company.repositories.FeedbacksRepository
import com.mpi.kissing_company.repositories.UserRepository
import com.mpi.kissing_company.utils.FeedbacksUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*


@RestController
internal class FeedbackController(private val repository: FeedbacksRepository,
                                  private val user_repository: UserRepository) {

    @Autowired
    private val feedbacksUtils = FeedbacksUtils()

    @GetMapping("/feedback/{girl_id}")
    fun getFeedbacks(@PathVariable girl_id: Long?): List<FeedbacksDto?> {
        return repository.findByGirlIdOrderByCreateAtDesc(girl_id).map { feedbacksUtils.mapToDto(it) }
    }


    @GetMapping("/feedback/avg/{girl_id}")
    fun getAvg(@PathVariable girl_id: Long?):Map<String, Double>  {
        val result_map = HashMap<String, Double>()
        val feedbacks_count = repository.countByGirlId(girl_id)
        val total_stars_sum = repository.sumStarsByGirlId(girl_id)
        result_map.put("stars_avg", total_stars_sum/feedbacks_count)
        return result_map
    }


    @PostMapping("/feedback")
    fun sendFeedback(auth: Authentication, @RequestBody newFeedback: FeedbacksDto) : FeedbacksDto{
        val user = user_repository.findByUsername(auth.name).get()
        newFeedback.setUsername(user.getUsername())
        val new_feedback = repository.save(feedbacksUtils.mapToEntity(newFeedback))
        return feedbacksUtils.mapToDto(new_feedback)
    }

}