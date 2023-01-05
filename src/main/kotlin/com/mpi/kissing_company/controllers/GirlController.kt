package com.mpi.kissing_company.controllers

import com.mpi.kissing_company.entities.Girl
import com.mpi.kissing_company.entities.InviteLinks
import com.mpi.kissing_company.exceptions.GirlNotFoundException
import com.mpi.kissing_company.repositories.GirlRepository
import com.mpi.kissing_company.repositories.InviteLinksRepository
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime
import java.util.*
import java.util.function.Function
import java.util.function.Supplier



@RestController
internal class GirlController(private val repository: GirlRepository,
                              private val invite_repository: InviteLinksRepository) {

    @GetMapping("/girls")
    fun all(): List<Girl?> {
        return repository.findAll()
    }

//    @PostMapping("/girls")
//    fun newGirl(@RequestBody newGirl: Girl): Girl{
//        return repository.save(newGirl)
//    }

    @GetMapping("/girls/{id}")
    fun one(@PathVariable id: Long): Girl? {
        return repository.findById(id)
//            .orElseThrow(Supplier<RuntimeException> { GirlNotFoundException(id) })
    }

    @PostMapping("/girls/registration/{token}")
    fun registrateGirl(@RequestBody newGirl: Girl, @PathVariable token: String): Girl {
        var invite_token: InviteLinks?
        try {
            invite_token = invite_repository.findByinviteToken(token)
        }
        catch(ex: EmptyResultDataAccessException){
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Invite token not found")
        }
        val now = LocalDateTime.now()
        if (now.isAfter(invite_token.getexpirtyDt())){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invite link is expired")  // FIXME: don't always return message
        }
//        this.deleteEntity(token)  // FIXME: do not work due to "No EntityManager with actual transaction available for current thread - cannot reliably process 'remove' call"

        return repository.save(newGirl)

    }

    @PutMapping("/girls/{id}")
    fun updateGirl(@RequestBody newGirl: Girl, @PathVariable id: String): Optional<Girl>? {
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

    @Transactional
    fun deleteEntity(token: String?) {
        invite_repository.deleteByinviteToken(token)
    }

}
