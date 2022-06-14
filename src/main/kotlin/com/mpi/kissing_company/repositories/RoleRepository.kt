package com.mpi.kissing_company.repositories

import com.mpi.kissing_company.entities.Roles
import org.springframework.data.jpa.repository.JpaRepository



interface RoleRepository : JpaRepository<Roles?, Int?> {
    fun findRoleByName(name: String?): Roles?
    fun findRoleById(id: Long?): Roles?
}
