package com.mpi.kissing_company.dto

import com.mpi.kissing_company.entities.Girl
import java.time.LocalDateTime

class GirlPhotoDto {
    private var id: Long? = null
    private var photoUri: String? = null
    private var girlId: Long? = null
    private var isProfilePhoto: Boolean? = false
    private var createDt: LocalDateTime? = null

    constructor(id: Long?, photoUri: String?, girlId: Long?, isProfilePhoto: Boolean? = false) {
        this.photoUri = photoUri
        this.girlId = girlId
        this.isProfilePhoto = isProfilePhoto
        this.id = id
    }

    fun getPhotoUri(): String?{
        return this.photoUri
    }

    fun getGirlId(): Long?{
        return this.girlId
    }

    fun getIsProfilePhoto(): Boolean?{
        return this.isProfilePhoto
    }

    fun getCreateDt(): LocalDateTime?{
        return this.createDt
    }

    fun setPhotoUri(uri: String){
        this.photoUri = uri
    }
//
//    fun setGirlId(){
//        return this.girlId
//    }
//
//    fun setIsProfilePhoto(){
//        return this.isProfilePhoto
//    }

}