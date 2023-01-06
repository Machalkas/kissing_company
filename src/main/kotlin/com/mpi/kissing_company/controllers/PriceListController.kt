package com.mpi.kissing_company.controllers

import com.mpi.kissing_company.dto.PriceListDto
import com.mpi.kissing_company.entities.Girl
import com.mpi.kissing_company.entities.PriceList
import com.mpi.kissing_company.repositories.PriceListRepository
import com.mpi.kissing_company.utils.PriceListUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*
import java.util.function.Function
import kotlin.collections.ArrayList

@RestController
internal class PriceListController(private val repository: PriceListRepository) {

    @Autowired
    private val priceListUtils = PriceListUtils()

    @GetMapping("/price_list/{id}")
    fun one(@PathVariable id: Long?): PriceListDto {
        val service = repository.findById(id).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Service not found") }

        val service_dto = priceListUtils.mapToDto(service)
        return service_dto
    }

    @GetMapping("/price_list")
    fun all(@RequestParam name: String? = null, @RequestParam girl: Girl? = null): List<PriceListDto> {
        val price_list_entity = repository.findAll()
        var price_list_dto = price_list_entity.map { priceListUtils.mapToDto(it) }
        return price_list_dto
    }


    @PostMapping("/price_list")
    fun newService(@RequestBody price_list_dto: PriceListDto): PriceListDto{
        val price_list_entity = priceListUtils.mapToEntety(price_list_dto)
        var saved = repository.save(price_list_entity)
        val id = saved.id
        val new_ent = repository.findById(id)
        return priceListUtils.mapToDto(new_ent.get())
    }

    @DeleteMapping("/price_list/{id}")
    @Transactional
    fun deleteService(@PathVariable id: Long?){
        if (repository.existsById(id) == false){
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Service not found")
        }
        repository.deleteById(id)
    }

    @PutMapping("/price_list/{id}")
    fun updateService(@RequestBody newService: PriceListDto, @PathVariable id: Long): PriceListDto{
        if (repository.existsById(id) == false){
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Service not found")
        }
        var current_service = repository.findById(id).get()
        current_service.serviceName = newService.getServiceName()
        current_service.cost = newService.getCost()
        current_service.isCostPerHour = newService.getIsCostPerHour()
        current_service.girl = priceListUtils.mapToEntety(newService).girl
        repository.save(current_service)
        return priceListUtils.mapToDto(current_service)
    }

}