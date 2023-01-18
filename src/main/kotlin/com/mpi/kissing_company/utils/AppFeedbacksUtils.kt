package com.mpi.kissing_company.utils

import com.mpi.kissing_company.dto.AppFeedbacksDto
import com.mpi.kissing_company.dto.FeedbacksDto
import com.mpi.kissing_company.dto.ServiceHistoryDetailsDto
import com.mpi.kissing_company.dto.ServiceHistoryDto
import com.mpi.kissing_company.entities.AppFeedbacks
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
class AppFeedbacksUtils {

    @Autowired
    private val userRepository: UserRepository? = null


    @Autowired
    private val userUtils = UserUtils()

    fun mapToDto(entity: AppFeedbacks?): AppFeedbacksDto {
        val dto = AppFeedbacksDto(
            id = entity?.id,
            stars = entity?.stars,
            user_username = entity?.user?.getUsername(),
            comment = entity?.comment,
            createAt = entity?.createAt,
            updateAt = entity?.updateAt
        )
        return dto
    }

    fun mapToEntity(dto: AppFeedbacksDto): AppFeedbacks {
        val user = userRepository?.findByUsername(dto.getUsername())?.orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "User not found") }

        val entity = AppFeedbacks(
            user = user,
            stars = dto.getStars(),
            comment = dto.getComment()
        )
        return entity
    }
}