package com.mpi.kissing_company.utils

import com.mpi.kissing_company.dto.FeedbacksDto
import com.mpi.kissing_company.dto.ServiceHistoryDetailsDto
import com.mpi.kissing_company.dto.ServiceHistoryDto
import com.mpi.kissing_company.entities.Feedbacks
import com.mpi.kissing_company.entities.ServiceHistory
import com.mpi.kissing_company.repositories.GirlRepository
import com.mpi.kissing_company.repositories.PriceListRepository
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
    private val girlUtils = GirlUtils()
    @Autowired
    private val userUtils = UserUtils()

    fun mapToDto(entity: Feedbacks?): FeedbacksDto {
        val dto = FeedbacksDto(
            id = entity?.id,
            girl_id = entity?.girl?.getId(),
            stars = entity?.stars,
            user_username = entity?.user?.getUsername(),
            comment = entity?.comment,
            createAt = entity?.createAt,
            updateAt = entity?.updateAt
        )
        return dto
    }

    fun mapToEntity(dto: FeedbacksDto): Feedbacks {
        val user = userRepository?.findByUsername(dto.getUsername())?.orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found") }
        val girl = girlRepository?.findById(dto.getGirlId())?.orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Girl not found") }

        val entity = Feedbacks(
            user = user,
            girl = girl,
            stars = dto.getStars(),
            comment = dto.getComment()
        )
        return entity
    }
}