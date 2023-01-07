package com.mpi.kissing_company.utils

import com.mpi.kissing_company.dto.PriceListDto
import com.mpi.kissing_company.entities.PriceList
import com.mpi.kissing_company.repositories.GirlRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class PriceListUtils{

    @Autowired
    private val girlRepository: GirlRepository? = null

    fun mapToDto(entity: PriceList?): PriceListDto{
        var dto = PriceListDto(
            id = entity?.id,
            girlId = entity?.girl?.getId(),
            serviceName = entity?.serviceName,
            cost = entity?.cost,
            isCostPerHour = entity?.isCostPerHour,
            create_at = entity?.create_at,
            update_at = entity?.update_at
        )
        return dto
    }

    fun mapToEntety(dto: PriceListDto): PriceList{
        if (dto.getGirlId()?.let { girlRepository?.existsById(it) } == false){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Girl not found")
        }
        val girl = dto.getGirlId()?.let { girlRepository?.findById(it.toLong()) }
        var entity = PriceList(
            girl = girl?.get(),
            serviceName = dto.getServiceName(),
            cost = dto.getCost(),
            isCostPerHour = dto.getIsCostPerHour()
        )
        return entity
    }
}