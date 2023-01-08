package com.mpi.kissing_company.controllers

import com.mpi.kissing_company.dto.ServiceHistoryDetailsDto
import com.mpi.kissing_company.dto.ServiceHistoryDto
import com.mpi.kissing_company.repositories.GirlRepository
import com.mpi.kissing_company.repositories.PriceListRepository
import com.mpi.kissing_company.repositories.ServiceHistoryRepository
import com.mpi.kissing_company.repositories.UserRepository
import com.mpi.kissing_company.utils.ServiceHistoryUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@RestController
internal class ServiceHistoryController(private val repository: ServiceHistoryRepository,
                                        private val user_repository: UserRepository,
                                        private val price_list_repository: PriceListRepository,
                                        private val girl_repository: GirlRepository,) {

    @Autowired
    private val serviceHistoryUtils = ServiceHistoryUtils()


    @GetMapping("/service_history")
    fun all(): List<ServiceHistoryDto> {
        val service_history_list = repository.findAll()
        return service_history_list.map { serviceHistoryUtils.mapToDto(it) }
    }

    @GetMapping("/service_history/{id}")
    fun getById(@PathVariable id: Long): ServiceHistoryDetailsDto {
        val service_history = repository.findById(id).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "ServiceHistory not found") }
        return serviceHistoryUtils.mapToDetailDto(service_history)
    }

    @GetMapping("/service_history/get_first_by_girl_id/{girl_id}")
    fun getFirstByGirl(@PathVariable girl_id: Long): ServiceHistoryDto{
        val girl = girl_repository.findById(girl_id).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Girl not found") }
        val service_history = repository.findFirstByGirlOrderByStartDtAsc(girl).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "ServiceHistory not found") }
        return serviceHistoryUtils.mapToDto(service_history)
    }

    @PostMapping("/service_history")
    fun createServiceHistory(auth: Authentication, @RequestBody newSeviceHistory: ServiceHistoryDto): ServiceHistoryDto {
        val user = user_repository.findByUsername(auth.name).get()
        if (user.getRole()?.name != "USER"){
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Only user with role \"USER\" allow")
        }
        val service = price_list_repository.findById(newSeviceHistory.getServiceId()).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Service not found") }
        val girl = service?.girl
        newSeviceHistory.setGirlId(girl?.getId())
        val now = LocalDateTime.now()
        val to: LocalDateTime = newSeviceHistory.getStartDt()!!
        val from: LocalDateTime = service?.estimatedDurationInMin?.let { to.minusMinutes(it) }!!
        if(now.isAfter(to)){
            throw throw ResponseStatusException(HttpStatus.BAD_REQUEST, "StartDt must be future date")
        }
        val test = repository.existsByGirlAndStartDtBetween(girl = girl, from = from, to = to)
        if (test){
            throw ResponseStatusException(HttpStatus.CONFLICT, "Already busy at this time")
        }
        if (service.isCostPerMinute == false){
            newSeviceHistory.setTotalCost(service.cost)
        }
        newSeviceHistory.setUsername(user.getUsername())
        newSeviceHistory.setStatus("created")
        val saved = repository.save(serviceHistoryUtils.mapToEntity(newSeviceHistory))
        val new_entity = saved.id?.let { repository.findById(it).get() }
        val new_dto = serviceHistoryUtils.mapToDto(new_entity)
        return new_dto
    }

}