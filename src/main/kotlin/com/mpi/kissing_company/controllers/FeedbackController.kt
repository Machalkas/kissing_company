package com.mpi.kissing_company.controllers

import com.mpi.kissing_company.dto.FeedbacksDto
import com.mpi.kissing_company.repositories.FeedbacksRepository
import com.mpi.kissing_company.repositories.GirlRepository
import com.mpi.kissing_company.repositories.UserRepository
import com.mpi.kissing_company.utils.FeedbacksUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException


@RestController
internal class FeedbackController(private val repository: FeedbacksRepository,
                                  private val user_repository: UserRepository,
                                    private val girl_repository: GirlRepository) {

    @Autowired
    private val feedbacksUtils = FeedbacksUtils()

    @GetMapping("/api/feedback/{girl_id}")
    fun getFeedbacks(@PathVariable girl_id: Long?): List<FeedbacksDto?> {
        if(girl_id?.let { girl_repository.existsById(it) } == false){
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Girl not found")
        }
        return repository.findByGirlIdOrderByCreateAtDesc(girl_id).map { feedbacksUtils.mapToDto(it) }
    }


    @GetMapping("/api/feedback/avg/{girl_id}")
    fun getAvg(@PathVariable girl_id: Long?):Map<String, Double>  {
        if(girl_id?.let { girl_repository.existsById(it) } == false){
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Girl not found")
        }
        val result_map = HashMap<String, Double>()
        val feedbacks_count = repository.countByGirlId(girl_id)
        val total_stars_sum = repository.sumStarsByGirlId(girl_id)
        var value = total_stars_sum / feedbacks_count
        if (value > 5){
            value = 5.0
        }
        result_map.put("stars_avg", value)
        return result_map
    }


    @PostMapping("/api/feedback")
    fun sendFeedback(auth: Authentication, @RequestBody newFeedback: FeedbacksDto) : FeedbacksDto{
        val user = user_repository.findByUsername(auth.name).get()
        newFeedback.setUsername(user.getUsername())
        val new_feedback = repository.save(feedbacksUtils.mapToEntity(newFeedback))
        return feedbacksUtils.mapToDto(new_feedback)
    }

}