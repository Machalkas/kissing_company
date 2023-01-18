package com.mpi.kissing_company.utils

import com.mpi.kissing_company.dto.CashbackDto
import com.mpi.kissing_company.dto.GirlDto
import com.mpi.kissing_company.entities.CashbackEntity
import com.mpi.kissing_company.entities.Girl
import com.mpi.kissing_company.entities.PriceList
import com.mpi.kissing_company.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class CashbackUtils{

    @Autowired
    private val userRepository: UserRepository? = null

    fun mapToDto(entity: CashbackEntity?): CashbackDto{
        var dto = CashbackDto(
            id = entity?.getId(),
            userUsername = entity?.getUser()?.getUsername(),
            slutCoins = entity?.getSlutCoins()
        )
        return dto
    }

    fun mapToEntity(dto: CashbackDto): CashbackEntity{
        if (dto.getUsername()?.let { userRepository?.existsByUsername(it) } == false){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found")
        }
        val user = userRepository?.findByUsername(dto.getUsername())
        var entity = CashbackEntity(
            user = user?.get(),
            slutCoins = dto.getSlutCoins()
        )
        return entity
    }
}