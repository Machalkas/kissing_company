package com.mpi.kissing_company.dto

import java.time.LocalDateTime

open class AppFeedbacksDto {
    private var id: Long? = null
    private var user_username: String? = null
    private var stars: Double? = null
    private var comment: String? = null
    private var createAt: LocalDateTime? = null
    private var updateAt: LocalDateTime? = null

    constructor(id: Long?, user_username: String?, stars:  Double?, comment: String?, createAt: LocalDateTime?, updateAt: LocalDateTime?) {
        this.id = id
        this.user_username = user_username
        this.comment = comment
        this.setStars(stars)
        this.createAt = createAt
        this.updateAt = updateAt
    }

    fun getId(): Long? {
        return this.id
    }
    fun getStars(): Double? {
        return this.stars
    }
    fun getUsername(): String? {
        return this.user_username
    }
    fun getComment(): String? {
        return this.comment
    }
    fun getCreateAt(): LocalDateTime? {
        return this.createAt
    }
    fun getUpdateAt(): LocalDateTime? {
        return this.updateAt
    }

    fun setUsername(username: String?){
        this.user_username = username
    }
    fun setComment(comment: String?){
        this.comment = comment
    }
    fun setStars(stars: Double?){
        if (stars != null && stars > 5){
            this.stars = 5.0
        }
        else if(stars != null && stars < 0){
            this.stars = 0.0
        }
        else{
            this.stars = stars
        }
    }
}