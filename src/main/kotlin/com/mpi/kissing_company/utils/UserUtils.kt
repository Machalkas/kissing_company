package com.mpi.kissing_company.utils

import com.mpi.kissing_company.dto.UserInfoDto
import com.mpi.kissing_company.entities.User
import com.mpi.kissing_company.repositories.RoleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException


@Service
class UserUtils {

    @Autowired
    private val roleRepository: RoleRepository? = null

    fun mapToDto(entity: User?): UserInfoDto{
        val dto = UserInfoDto(
            firstName = entity?.getfirst_name(),
            secondName = entity?.getsecond_name(),
            roleName = entity?.getRole()?.name,
            username = entity?.getUsername()
        )
        return dto
    }

    fun mapToEntity(dto: UserInfoDto?): User{
        if (dto?.getRoleName()?.let { roleRepository?.existsByName(it) } == false){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Role not found")
        }

        val role = roleRepository?.findByName(dto?.getRoleName())
        val entity = User()
        entity.setRole(role)
        entity.setfirst_name(dto?.getFirstName())
        entity.setsecond_name(dto?.getSecondName())
        return entity
    }
}