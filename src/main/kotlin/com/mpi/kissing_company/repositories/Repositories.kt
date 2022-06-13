package com.mpi.kissing_company.repositories

import com.mpi.kissing_company.entities.Users
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource


@RepositoryRestResource(collectionResourceRel = "people", path = "people")
interface PersonRepository : PagingAndSortingRepository<Users?, Long?> {
    fun findByUsername(@Param("username") username: String?): List<Users?>?
}