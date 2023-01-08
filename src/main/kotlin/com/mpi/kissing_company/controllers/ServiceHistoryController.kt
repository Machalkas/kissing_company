package com.mpi.kissing_company.controllers

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
    fun getById(@PathVariable id: Long): ServiceHistoryDto {
        val service_history = repository.findById(id).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "ServiceHistory not found") }
        return serviceHistoryUtils.mapToDto(service_history)
    }

//    @PostMapping("/service_history")
//    fun createServiceHistory(auth: Authentication, @RequestBody newSeviceHistory: ServiceHistoryDto): ServiceHistoryDto {
//        val user = user_repository.findByUsername(auth.name).get()
//        if (user.getRole()?.name != "USER"){
//            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Only user with role \"USER\" allow")
//        }
//        newSeviceHistory.setUsername(user.getUsername())
//
//    }

}