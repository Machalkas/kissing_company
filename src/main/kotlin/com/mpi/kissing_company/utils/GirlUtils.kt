package com.mpi.kissing_company.utils

import com.mpi.kissing_company.dto.GirlDto
import com.mpi.kissing_company.entities.Girl
import com.mpi.kissing_company.entities.PriceList
import com.mpi.kissing_company.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class GirlUtils{

    @Autowired
    private val userRepository: UserRepository? = null

    fun mapToDto(entity: Girl?): GirlDto{
        var dto = GirlDto(
            id = entity?.getId(),
            userUsername = entity?.getUser()?.getUsername(),
            location = entity?.getLocation(),
            age = entity?.getAge(),
            height = entity?.getHeight(),
            weight = entity?.getWeight(),
            nation = entity?.getNation(),
            telephone = entity?.getTelephone(),
            hair_color = entity?.getHair_color(),
            nickname = entity?.getNikname()
        )
        return dto
    }

    fun mapToEntety(dto: GirlDto): Girl{
        if (dto.getUsername()?.let { userRepository?.existsByUsername(it) } == false){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found")
        }
        val user = userRepository?.findByUsername(dto.getUsername())
        var entity = Girl(
            user = user?.get(),
            location = dto.getLocation(),
            age = dto.getAge(),
            height = dto.getHeight(),
            weight = dto.getWeight(),
            nation = dto.getNation(),
            telephone = dto.getTelephone(),
            hair_color = dto.getHair_color(),
            nickname = dto.getNikname()
        )
        return entity
    }
}