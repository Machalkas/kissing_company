package com.mpi.kissing_company.controllers

import com.mpi.kissing_company.entities.InviteLinks
import com.mpi.kissing_company.repositories.InviteLinksRepository
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
internal class InviteLinksController(private val repository: InviteLinksRepository) {
    @PostMapping("/invite_link")
    fun generate(): InviteLinks{
        val link = InviteLinks()
        repository.save(link)
        return repository.findByinviteToken(link.getinvateToken())
    }

    @GetMapping("/invite_link")
    fun all(): List<InviteLinks?>{
        return repository.findAll()
    }

    @DeleteMapping("/invite_link/{inviteToken}")
    fun delete(@PathVariable inviteToken: String){
        repository.deleteByinviteToken(inviteToken)
    }
}
