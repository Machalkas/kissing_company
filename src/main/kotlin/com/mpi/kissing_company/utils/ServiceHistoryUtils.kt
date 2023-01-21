package com.mpi.kissing_company.utils

import com.mpi.kissing_company.dto.ServiceHistoryDetailsDto
import com.mpi.kissing_company.dto.ServiceHistoryDto
import com.mpi.kissing_company.entities.ServiceHistory
import com.mpi.kissing_company.repositories.*
import com.mpi.kissing_company.repositories.GirlRepository
import com.mpi.kissing_company.repositories.PaymentTokensRepository
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
    @Autowired
    private val paymentTokensRepository: PaymentTokensRepository? = null
    @Autowired
    private val feedbacksRepository: FeedbacksRepository? = null

    @Autowired
    private val girlUtils = GirlUtils()
    @Autowired
    private val userUtils = UserUtils()
    @Autowired
    private val priceListUtils = PriceListUtils()
    @Autowired
    private val paymentTokensUtils = PaymentTokensUtils()
    @Autowired
    private var feedbacksUtils = FeedbacksUtils()

    fun mapToDto(entity: ServiceHistory?): ServiceHistoryDto {
        val dto = ServiceHistoryDto(
            id = entity?.id,
            girl_id = entity?.getGirl()?.getId(),
            service_id = entity?.getService()?.id,
            user_username = entity?.getClient()?.getUsername(),
            startDt = entity?.getStartDt(),
            endDt = entity?.getEndDt(),
            totalCost = entity?.getTotalCost(),
            status = entity?.getStatus(),
            createAt = entity?.createAt,
            updateAt = entity?.updateAt
        )
        return dto
    }

    fun mapToEntity(dto: ServiceHistoryDto): ServiceHistory {
        val user = userRepository?.findByUsername(dto.getUsername())?.orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found") }
        val girl = girlRepository?.findById(dto.getGirlId())?.orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Girl not found") }
        val service = priceListRepository?.findById(dto.getServiceId())?.orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Service not found") }

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

    fun mapToDetailDto(entity: ServiceHistory?): ServiceHistoryDetailsDto {
        val dto = ServiceHistoryDetailsDto(
            id = entity?.id,
            girl_id = entity?.getGirl()?.getId(),
            service_id = entity?.getService()?.id,
            user_username = entity?.getClient()?.getUsername(),
            startDt = entity?.getStartDt(),
            endDt = entity?.getEndDt(),
            totalCost = entity?.getTotalCost(),
            status = entity?.getStatus(),
            createAt = entity?.createAt,
            updateAt = entity?.updateAt
        )
        dto.setGirlDto(girlUtils.mapToDto(
            girlRepository?.findById(dto.getGirlId())?.orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Girl not found") }
        ))
        dto.setClientDto(userUtils.mapToDto(
            userRepository?.findByUsername(dto.getUsername())?.orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "User not found") }
        ))
        dto.setPriceListDto(priceListUtils.mapToDto(
            priceListRepository?.findById(dto.getServiceId())?.orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Service not found") }
        ))
        dto.setPaymentTokensDto(paymentTokensUtils.mapToDto(
            paymentTokensRepository?.findByserviceHistory(entity)
        ))
        dto.setFeedbacksDto(feedbacksUtils.mapToDto(feedbacksRepository?.findByServiceHistoryId(entity?.id)))
        return dto
    }
}