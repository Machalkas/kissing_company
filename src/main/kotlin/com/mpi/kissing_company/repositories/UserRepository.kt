package com.mpi.kissing_company.repositories

import com.mpi.kissing_company.entities.User
import org.springframework.data.jpa.repository.JpaRepository

internal interface UserRepository : JpaRepository<User?, String>