package com.mpi.kissing_company.repositories

import com.mpi.kissing_company.entities.User
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource


//@RepositoryRestResource(collectionResourceRel = "users", path = "users")
//interface PersonRepository : PagingAndSortingRepository<User?, Long?> {
//    fun findByUsername(@Param("username") username: String?): List<User?>?
//}