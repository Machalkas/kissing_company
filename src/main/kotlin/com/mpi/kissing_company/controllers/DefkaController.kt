package com.mpi.kissing_company.controllers

import com.mpi.kissing_company.entities.Defka
import com.mpi.kissing_company.entities.User
import com.mpi.kissing_company.repositories.DefkaRepository
import org.springframework.web.bind.annotation.GetMapping
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne


internal class DefkaController(private val repository: DefkaRepository) {
    @GetMapping("/girls")
    fun all(): List<Defka?> {
        return repository.findAll()
    }
}