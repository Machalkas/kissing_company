package com.mpi.kissing_company.controllers

import com.mpi.kissing_company.entities.InviteLinks
import com.mpi.kissing_company.repositories.InviteLinksRepository
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController


@RestController
internal class InviteLinksController(private val repository: InviteLinksRepository) {
    @PostMapping("/generate_invite_link")
    fun generate(): InviteLinks{
        val link = InviteLinks()
        repository.save(link)
        return repository.findByinviteToken(link.getinvateToken())
    }
}