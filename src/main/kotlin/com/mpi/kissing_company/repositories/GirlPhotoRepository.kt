package com.mpi.kissing_company.repositories

import com.mpi.kissing_company.entities.Girl
import com.mpi.kissing_company.entities.GirlPhoto
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

internal interface GirlPhotoRepository : JpaRepository<GirlPhoto?, String>{
    fun findById(id: Long?): Optional<GirlPhoto?>
    fun findByGirlOrderByCreateDtDesc(girl: Girl): List<GirlPhoto?>
    fun findByGirlAndIsProfilePhotoOrderByCreateDtDesc(girl: Girl, isProfilePhoto: Boolean): List<GirlPhoto?>
    fun findByIsProfilePhoto(isProfilePhoto: Boolean): List<GirlPhoto>
    fun existsByGirl(girl: Girl): Boolean
    fun existsById(id: Long): Boolean
    fun deleteById(id: Long)
}
