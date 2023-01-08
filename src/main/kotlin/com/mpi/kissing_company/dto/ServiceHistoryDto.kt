package com.mpi.kissing_company.dto

import java.time.LocalDateTime

class ServiceHistoryDto {
    private var id: Long? = null
    private var girl_id: Long? = null
    private var service_id: Long? = null
    private var user_username: String? = null
    private var startDt: LocalDateTime? = null
    private var endDt: LocalDateTime? = null
    private var totalCost: Float? = null
    private var status: String? = null
    private var createAt: LocalDateTime? = null
    private var updateAt: LocalDateTime? = null

    constructor(id: Long?, girl_id: Long?, service_id: Long?, user_username: String?, startDt: LocalDateTime?,
                totalCost: Float?, status: String?, endDt: LocalDateTime?) {
        this.id = id
        this.girl_id = girl_id
        this.service_id = service_id
        this.user_username = user_username
        this.startDt = startDt
        this.totalCost = totalCost
        this.status = status
        this.endDt = endDt
    }

    fun getGirlId(): Long? {
        return this.girl_id
    }
    fun getServiceId(): Long? {
        return this.service_id
    }
    fun getUsername(): String? {
        return this.user_username
    }
    fun getStartDt(): LocalDateTime? {
        return this.startDt
    }
    fun getTotalCost(): Float? {
        return this.totalCost
    }
    fun getStatus(): String? {
        return this.status
    }
    fun getEndDt(): LocalDateTime? {
        return this.endDt
    }

    fun setUsername(username: String?){
        this.user_username = username
    }
}