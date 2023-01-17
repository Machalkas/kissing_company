package com.mpi.kissing_company.repositories

import com.mpi.kissing_company.entities.Girl
import com.mpi.kissing_company.entities.GirlPhoto
import com.mpi.kissing_company.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

internal interface GirlPhotoRepository : JpaRepository<GirlPhoto?, String>{
    fun findById(id: Long?): Optional<GirlPhoto?>
    fun findByGirl(girl: Girl): List<GirlPhoto?>
    fun findByIsProfilePhoto(isProfilePhoto: Boolean): List<GirlPhoto>
    fun existsByGirl(girl: Girl): Boolean
    fun existsById(id: Long): Boolean
    fun deleteById(id: Long)
}
