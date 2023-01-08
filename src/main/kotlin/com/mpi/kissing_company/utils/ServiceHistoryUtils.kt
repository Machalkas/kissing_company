package com.mpi.kissing_company.utils

import com.mpi.kissing_company.dto.ServiceHistoryDto
import com.mpi.kissing_company.entities.ServiceHistory
import com.mpi.kissing_company.repositories.GirlRepository
import com.mpi.kissing_company.repositories.PriceListRepository
import com.mpi.kissing_company.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ServiceHistoryUtils {

    @Autowired
    private val userRepository: UserRepository? = null
    @Autowired
    private val girlRepository: GirlRepository? = null
    @Autowired
    private val priceListRepository: PriceListRepository? = null

    fun mapToDto(entity: ServiceHistory?): ServiceHistoryDto {
        val dto = ServiceHistoryDto(
            id = entity?.id,
            girl_id = entity?.getGirl()?.getId(),
            service_id = entity?.getService()?.id,
            user_username = entity?.getClient()?.getUsername(),
            startDt = entity?.getEndDt(),
            endDt = entity?.getEndDt(),
            totalCost = entity?.getTotalCost(),
            status = entity?.getStatus()
        )
        return dto
    }

    fun mapToEntity(dto: ServiceHistoryDto): ServiceHistory {
        val user = userRepository?.findByUsername(dto.getUsername())?.orElseThrow { ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer not found") }
        val girl = girlRepository?.findById(dto.getGirlId())?.orElseThrow { ResponseStatusException(HttpStatus.BAD_REQUEST, "Girl not found") }
        val service = priceListRepository?.findById(dto.getServiceId())?.orElseThrow { ResponseStatusException(HttpStatus.BAD_REQUEST, "Service not found") }

        val entity = ServiceHistory(
            client = user,
            girl = girl,
            service = service,
            status = dto.getStatus(),
            startDt = dto.getStartDt(),
            totalCost = dto.getTotalCost()
        )
        return entity
    }
}