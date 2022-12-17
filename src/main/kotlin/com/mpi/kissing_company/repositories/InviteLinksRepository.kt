package com.mpi.kissing_company.repositories

import com.mpi.kissing_company.entities.InviteLinks
import org.springframework.data.jpa.repository.JpaRepository

internal interface InviteLinksRepository: JpaRepository<InviteLinks?, String> {
    fun findByinviteToken(token: String?): InviteLinks
    fun deleteByinviteToken(token: String?)
}