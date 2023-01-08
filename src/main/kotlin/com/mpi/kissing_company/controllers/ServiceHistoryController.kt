package com.mpi.kissing_company.controllers

import com.mpi.kissing_company.dto.ServiceHistoryDetailsDto
import com.mpi.kissing_company.dto.ServiceHistoryDto
import com.mpi.kissing_company.repositories.GirlRepository
import com.mpi.kissing_company.repositories.PriceListRepository
import com.mpi.kissing_company.repositories.ServiceHistoryRepository
import com.mpi.kissing_company.repositories.UserRepository
import com.mpi.kissing_company.utils.ServiceHistoryUtils
import com.mpi.kissing_company.utils.UserUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@RestController
internal class ServiceHistoryController(private val repository: ServiceHistoryRepository,
                                        private val user_repository: UserRepository,
                                        private val price_list_repository: PriceListRepository,
                                        private val girl_repository: GirlRepository,) {

    @Autowired
    private val serviceHistoryUtils = ServiceHistoryUtils()
    @Autowired
    private val userUtils = UserUtils()


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

    @GetMapping("/service_history/get_nearest_by_girl_id/{girl_id}")
    fun getFirstByGirl(@PathVariable girl_id: Long): ServiceHistoryDetailsDto{
        val girl = girl_repository.findById(girl_id).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Girl not found") }
        val service_history = repository.findFirstByGirlOrderByStartDtAsc(girl).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "ServiceHistory not found") }
        return serviceHistoryUtils.mapToDetailDto(service_history)
    }

    @GetMapping("/service_history/get_nearest_by_client_username/{user_username}")
    fun getFirstByUser(@PathVariable user_username: String): ServiceHistoryDetailsDto{
        val client = user_repository.findById(user_username).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "User not found") }
        val service_history = repository.findFirstByClientOrderByStartDtAsc(client).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "ServiceHistory not found") }
        return serviceHistoryUtils.mapToDetailDto(service_history)
    }

    @GetMapping("/service_history/get_all_for_client/{user_username}")
    fun getAllByUser(@PathVariable user_username: String): List<ServiceHistoryDto>{
        val client = user_repository.findById(user_username).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "User not found") }
        val service_history = repository.findByClientOrderByStartDtAsc(client)
        return service_history.map { serviceHistoryUtils.mapToDto(it) }
    }

    @GetMapping("/service_history/get_all_for_girl/{girl_id}")
    fun getAllByGirl(@PathVariable girl_id: Long): List<ServiceHistoryDto>{
        val girl = girl_repository.findById(girl_id).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Girl not found") }
        val service_history = repository.findByGirlOrderByStartDtAsc(girl)
        return service_history.map { serviceHistoryUtils.mapToDto(it) }
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

    @PutMapping("/service_history/cancel_appointment/{service_id}")
    @Transactional
    fun cancelAppointment(auth: Authentication, @PathVariable service_id: Long){
        val user = user_repository.findByUsername(auth.name).get()
        val service_history = repository.findById(service_id).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "ServiceHistory not found") }
        if (service_history?.getClient()?.getUsername() != user.getUsername() && user.getRole()?.name != "ADMIN"){
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Not enough rights")
        }
        if (service_history?.getStatus() !in listOf("CREATED", "APPROVED")){
            throw ResponseStatusException(HttpStatus.CONFLICT, "U can cancel appointment only if status in "+listOf("CREATED", "APPROVED"))
        }

        val user_dto = userUtils.mapToDto(user)
        if (user.getRole()?.name == "HOOKER"){
            service_history?.setStatus("CANCEL_BY_GIRL")
        }
        else if (user.getRole()?.name == "USER"){
            service_history?.setStatus("CANCEL_BY_CLIENT")
        }
        else if (user.getRole()?.name == "ADMIN"){
            service_history?.setStatus("CANCEL_BY_ADMIN")
        }
        else{
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Only user or girl can cancel appointment")
        }
        repository.save(service_history)
    }

    @PutMapping("/service_history/approve/{service_id}")
    @Transactional
    fun approveAppointment(auth: Authentication, @PathVariable service_id: Long) {
        val user = user_repository.findByUsername(auth.name).get()
        val service_history = repository.findById(service_id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "ServiceHistory not found") }
        if (service_history?.getGirl()?.getUser()
                ?.getUsername() != user.getUsername() && user.getRole()?.name != "ADMIN"
        ) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Not enough rights")
        }
        if (service_history?.getStatus() != "CREATED") {
            throw ResponseStatusException(HttpStatus.CONFLICT, "U can approve appointment only if status == CREATED")
        }
        service_history?.setStatus("APPROVED")
        repository.save(service_history)
    }

    @PutMapping("/service_history/start/{service_id}")
    @Transactional
    fun startAppointment(auth: Authentication, @PathVariable service_id: Long) {
        val user = user_repository.findByUsername(auth.name).get()
        val service_history = repository.findById(service_id).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "ServiceHistory not found") }
        if (service_history?.getClient()?.getUsername() != user.getUsername() && user.getRole()?.name != "ADMIN"){
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Not enough rights")
        }
        if (service_history?.getStatus() != "APPROVED"){
            throw ResponseStatusException(HttpStatus.CONFLICT, "U can cancel appointment only if status == APPROVED")
        }
        service_history?.setStatus("STARTED")
        repository.save(service_history)
    }

    @PutMapping("/service_history/stop/{service_id}")
    @Transactional
    fun stopAppointment(auth: Authentication, @PathVariable service_id: Long):  ServiceHistoryDetailsDto {
        val user = user_repository.findByUsername(auth.name).get()
        val service_history = repository.findById(service_id).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "ServiceHistory not found") }
        if (service_history?.getClient()?.getUsername() != user.getUsername() && user.getRole()?.name != "ADMIN"){
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Not enough rights")
        }
        if (service_history?.getStatus() != "STARTED"){
            throw ResponseStatusException(HttpStatus.CONFLICT, "U can cancel appointment only if status == STARTED")
        }
        service_history?.setStatus("ENDED")
        service_history?.setEndDt(LocalDateTime.now())
        if (service_history?.getService()?.isCostPerMinute == true){
            val min_pass = ChronoUnit.MINUTES.between(service_history.getEndDt(), service_history.getStartDt())
            service_history.setTotalCost(service_history?.getService()?.cost?.times(min_pass))
        }
        val saved = repository.save(service_history)
        val new_entity = saved.id?.let { repository.findById(it).get() }
        val new_dto = serviceHistoryUtils.mapToDetailDto(new_entity)
        return new_dto
    }
}