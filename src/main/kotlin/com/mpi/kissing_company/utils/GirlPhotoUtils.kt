package com.mpi.kissing_company.utils

import com.mpi.kissing_company.dto.GirlPhotoDto
import com.mpi.kissing_company.dto.GirlPhotoRetrieveDto
import com.mpi.kissing_company.entities.GirlPhoto
import com.mpi.kissing_company.repositories.GirlRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class GirlPhotoUtils {

    @Autowired
    private val girlRepository: GirlRepository? = null

    fun mapToDto(entity: GirlPhoto?): GirlPhotoDto{
        var dto = GirlPhotoDto(
            id = entity?.getId(),
            photoUri = entity?.getPhotoUri(),
            girlId = entity?.getGirl()?.getId(),
            isProfilePhoto = entity?.getIsProfilePhoto(),
            createDt = entity?.getCreateDt()
        )
        return dto
    }

    fun mapToEntity(dto: GirlPhotoDto): GirlPhoto{
        val girl = girlRepository?.findById(dto.getGirlId())
        var entity = GirlPhoto(
            photoUri = dto.getPhotoUri(),
            girl = girl?.orElseThrow { ResponseStatusException(HttpStatus.BAD_REQUEST, "Girl not found") },
            isProfilePhoto = dto.getIsProfilePhoto()
        )
        return entity
    }

    fun mapToRetrieveDto(entity: GirlPhoto?): GirlPhotoRetrieveDto {
        val dto = GirlPhotoRetrieveDto(
            id = entity?.getId(),
            girlId = entity?.getGirl()?.getId(),
            isProfilePhoto = entity?.getIsProfilePhoto(),
            createDt = entity?.getCreateDt()
        )
        return dto
    }
}