package com.mpi.kissing_company.repositories

import com.mpi.kissing_company.entities.Role
import org.springframework.data.jpa.repository.JpaRepository

internal interface RoleRepository : JpaRepository<Role?, String>