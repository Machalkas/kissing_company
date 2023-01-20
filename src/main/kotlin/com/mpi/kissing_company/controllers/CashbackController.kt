package com.mpi.kissing_company.controllers

import com.mpi.kissing_company.dto.CashbackDto
import com.mpi.kissing_company.dto.GirlDto
import com.mpi.kissing_company.entities.CashbackEntity
import com.mpi.kissing_company.entities.InviteLinks
import com.mpi.kissing_company.repositories.*
import com.mpi.kissing_company.repositories.InviteLinksRepository
import com.mpi.kissing_company.repositories.UserRepository
import com.mpi.kissing_company.utils.CashbackUtils
import com.mpi.kissing_company.utils.GirlUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime



@RestController
internal class CashbackController(private val repository: CashbackRepository,
                                  private val invite_repository: InviteLinksRepository,
                                  private val user_repository: UserRepository) {

    @Autowired
    private val cashbackUtils = CashbackUtils()




    @GetMapping("/api/cashback")
    fun getUserCashback(auth: Authentication): CashbackDto {
        val user = user_repository.findByUsername(auth.name).get()
        var cashback: CashbackEntity
        try {
            cashback = repository.findByUser(user).get()
        } catch (ex: NoSuchElementException){
            cashback = repository.save(CashbackEntity(user, 0.0F))
        }
        return cashbackUtils.mapToDto(cashback)
    }



}
