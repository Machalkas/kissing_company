package com.mpi.kissing_company.controllers

import com.mpi.kissing_company.entities.Girl
import com.mpi.kissing_company.exceptions.GirlNotFoundException
import com.mpi.kissing_company.repositories.GirlRepository
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.function.Function
import java.util.function.Supplier


@RestController
internal class GirlController(private val repository: GirlRepository) {

    @GetMapping("/girls")
    fun all(): List<Girl?> {
        return repository.findAll()
    }

    @PostMapping("/girls")
    fun newGirl(@RequestBody newGirl: Girl): Girl{
        return repository.save(newGirl)
    }

    @GetMapping("/girls/{id}")
    fun one(@PathVariable id: String): Girl? {
        return repository.findById(id)
            .orElseThrow(Supplier<RuntimeException> { GirlNotFoundException(id) })
    }

    @PutMapping("/girls/{id}")
    fun replaceGirl(@RequestBody newGirl: Girl, @PathVariable id: String): Optional<Girl>? {
        return repository.findById(id)
            .map<Girl>(Function { girl: Girl ->
                girl.setAge(newGirl.getAge())
                girl.setHair_color(newGirl.getHair_color())
                girl.setNation(newGirl.getNation())
                girl.setHeight(newGirl.getHeight())
                girl.setWeight(newGirl.getWeight())
                girl.setLocation(newGirl.getLocation())
                girl.setTelephone(newGirl.getTelephone())
                girl.setUser(newGirl.getUser())
                
                repository.save(girl)} as (Girl?) -> Girl)
    }

    @DeleteMapping("/girls/{id}")
    fun deleteGirlname(@PathVariable id: String){
        repository.deleteById(id)
    }
}
