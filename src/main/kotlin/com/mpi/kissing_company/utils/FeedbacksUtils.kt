package com.mpi.kissing_company.utils

import com.mpi.kissing_company.dto.FeedbacksDto
import com.mpi.kissing_company.dto.ServiceHistoryDetailsDto
import com.mpi.kissing_company.dto.ServiceHistoryDto
import com.mpi.kissing_company.entities.Feedbacks
import com.mpi.kissing_company.entities.ServiceHistory
import com.mpi.kissing_company.repositories.GirlRepository
import com.mpi.kissing_company.repositories.PriceListRepository
import com.mpi.kissing_company.repositories.ServiceHistoryRepository
import com.mpi.kissing_company.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class FeedbacksUtils {

    @Autowired
    private val userRepository: UserRepository? = null
    @Autowired
    private val girlRepository: GirlRepository? = null
    @Autowired
    private val serviceHistory: ServiceHistoryRepository? = null

    @Autowired
    private val girlUtils = GirlUtils()
    @Autowired
    private val userUtils = UserUtils()

    fun mapToDto(entity: Feedbacks?): FeedbacksDto {
        val service_history = entity?.serviceHistory?.id?.let { this.serviceHistory?.findById(it)
            ?.orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "ServiceHistory not found") } }
        val dto = FeedbacksDto(
            id = entity?.id,
            girl_id = service_history?.getGirl()?.getId(),
            stars = entity?.stars,
            user_username = entity?.user?.getUsername(),
            comment = entity?.comment,
            service_history_id = entity?.serviceHistory?.id,
            createAt = entity?.createAt,
            updateAt = entity?.updateAt
        )
        return dto
    }

    fun mapToEntity(dto: FeedbacksDto): Feedbacks {
        val service_history = dto.getServiceHistoryId()?.let { serviceHistory?.findById(it) }
            ?.orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "ServiceHistory not found") }
        val user = userRepository?.findByUsername(dto.getUsername())?.orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found") }
        val girl = service_history?.getGirl()
        val entity = Feedbacks(
            user = user,
            girl = girl,
            stars = dto.getStars(),
            comment = dto.getComment(),
            serviceHistory = service_history
        )
        return entity
    }
}