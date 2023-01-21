package com.mpi.kissing_company.controllers

import com.mpi.kissing_company.dto.FeedbacksDto
import com.mpi.kissing_company.repositories.FeedbacksRepository
import com.mpi.kissing_company.repositories.GirlRepository
import com.mpi.kissing_company.repositories.ServiceHistoryRepository
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
                                    private val girl_repository: GirlRepository,
private val service_repository: ServiceHistoryRepository) {

    @Autowired
    private val feedbacksUtils = FeedbacksUtils()

    @GetMapping("/api/feedback/girl/{girl_id}")
    fun getGirlsFeedbacks(@PathVariable girl_id: Long?): List<FeedbacksDto?> {
        if(girl_id?.let { girl_repository.existsById(it) } == false){
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Girl not found")
        }
        return repository.findByGirlIdOrderByCreateAtDesc(girl_id).map { feedbacksUtils.mapToDto(it) }
    }


    @GetMapping("/api/feedback/service_history/{service_id}")
    fun getServiceFeedbacks(@PathVariable service_id: Long?): FeedbacksDto?{
        if(service_id?.let { service_repository.existsById(it) } == false){
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "ServiceHistory not found")
        }
        return feedbacksUtils.mapToDto(repository.findByServiceHistoryIdOrderByCreateAtDesc(service_id))
    }


    @GetMapping("/api/feedback/avg/{girl_id}")
    fun getAvg(@PathVariable girl_id: Long?):Map<String, Double>  {
        if(girl_id?.let { girl_repository.existsById(it) } == false){
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Girl not found")
        }
        var value: Double = 4.5
        val result_map = HashMap<String, Double>()
        val feedbacks_count = repository.countByGirlId(girl_id)
        var total_stars_sum = repository.sumStarsByGirlId(girl_id)
        if (total_stars_sum != null){
            value = total_stars_sum / feedbacks_count
        }
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
        try {
            val new_feedback = repository.save(feedbacksUtils.mapToEntity(newFeedback))
            return feedbacksUtils.mapToDto(new_feedback)
        } catch (ex: org.springframework.dao.DataIntegrityViolationException){
            throw ResponseStatusException(HttpStatus.CONFLICT, "U can create only one feedback per order")
        }
    }

}