package com.mpi.kissing_company.repositories

import com.mpi.kissing_company.entities.InviteLinks
import org.springframework.data.jpa.repository.JpaRepository

interface InviteLinksRepository: JpaRepository<InviteLinks, String> {
    fun findByinvite_token(token: String): InviteLinks
    fun existsByinvite_token(token: String): Boolean
}