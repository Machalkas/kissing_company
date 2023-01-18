package com.mpi.kissing_company.controllers

import com.mpi.kissing_company.entities.InviteLinks
import com.mpi.kissing_company.repositories.InviteLinksRepository
import org.springframework.http.HttpStatus
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException


@RestController
internal class InviteLinksController(private val repository: InviteLinksRepository) {
    @PostMapping("/invite_link")
    fun generate(): InviteLinks{
        val link = InviteLinks()
        repository.save(link)
        return repository.findByinviteToken(link.getinvateToken())
    }

    @GetMapping("/invite_link")
    @Transactional
    fun all(): List<InviteLinks?>{
        repository.deleteByExpiryDtLessThan()
        return repository.findAll()
    }

    @DeleteMapping("/invite_link/{inviteToken}")
    @Transactional
    fun delete(@PathVariable inviteToken: String){
        if (repository.existsById(inviteToken) == false){
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Invite token not found")
        }
        repository.deleteByinviteToken(inviteToken)
    }
}
