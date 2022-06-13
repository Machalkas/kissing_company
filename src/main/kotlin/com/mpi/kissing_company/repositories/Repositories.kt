package com.mpi.kissing_company.repositories

import com.mpi.kissing_company.entities.User
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource


@RepositoryRestResource(collectionResourceRel = "people", path = "people")
interface PersonRepository : PagingAndSortingRepository<User?, Long?> {
    fun findByNickname(@Param("nickname") nickname: String?): List<User?>?
}