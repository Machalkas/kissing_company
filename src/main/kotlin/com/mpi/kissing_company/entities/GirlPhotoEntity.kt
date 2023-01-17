package com.mpi.kissing_company.entities

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name="girls_photos")
class GirlPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long? = null

    @Column(unique = true, nullable = false)
    private var photoUri: String? = null

    @ManyToOne(optional = false, cascade = arrayOf(CascadeType.REMOVE))
    private var girl: Girl? = null
    private var isProfilePhoto: Boolean? = false

    @Column(name="create_dt")
    private var createDt: LocalDateTime? = null

    @PrePersist
    protected fun onCreate() {
        createDt = LocalDateTime.now()
    }

    constructor(photoUri: String?, girl: Girl?, isProfilePhoto: Boolean? = false) {
        this.photoUri = photoUri
        this.girl = girl
        this.isProfilePhoto = isProfilePhoto
    }

    fun getId(): Long?{
        return this.id
    }

    fun getPhotoUri(): String?{
        return this.photoUri
    }

    fun getGirl(): Girl?{
        return this.girl
    }

    fun getIsProfilePhoto(): Boolean?{
        return this.isProfilePhoto
    }

    fun getCreateDt(): LocalDateTime?{
        return this.createDt
    }

}