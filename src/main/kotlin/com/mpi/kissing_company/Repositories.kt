package com.mpi.kissing_company

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource


@RepositoryRestResource(collectionResourceRel = "people", path = "people")
interface UserRepo: PagingAndSortingRepository<User, Long>{
    fun findByNickname(nickname: String): User?
}
