package com.mpi.kissing_company.controllers

import com.mpi.kissing_company.entities.Girl
import com.mpi.kissing_company.entities.PriceList
import com.mpi.kissing_company.repositories.PriceListRepository
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
internal class PriceListController(private val repository: PriceListRepository) {

    @GetMapping("/price_list")
    fun all(@RequestParam name: String? = null, @RequestParam girl: Girl? = null): List<PriceList?> {
        return repository.findAll()
    }

    @GetMapping("/price_list/{id}")
    fun one(@PathVariable id: Long?): Optional<PriceList?> {
        return repository.findById(id)
    }

    @PostMapping("/price_list")
    fun newPriceItem(@RequestBody price_list: PriceList): PriceList{
        return repository.save(price_list)
//        return repository.findById(price_list.id)
    }
}