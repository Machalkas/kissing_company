package com.mpi.kissing_company.repositories

import com.mpi.kissing_company.entities.InviteLinks
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

internal interface InviteLinksRepository: JpaRepository<InviteLinks?, String> {
    fun findByinviteToken(token: String?): InviteLinks
    fun deleteByinviteToken(token: String?)
    fun deleteByExpiryDtLessThan(ldt: LocalDateTime? = LocalDateTime.now())
}

