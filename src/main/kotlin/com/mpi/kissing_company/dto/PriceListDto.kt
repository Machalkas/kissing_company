package com.mpi.kissing_company.dto

import lombok.Data
import java.time.LocalDateTime


@Data
class PriceListDto {
    private var id: Long? = null
    private var girlId: Long? = null
    private var serviceName: String? = null
    private var cost: Float? = null
    private var isCostPerMinute: Boolean? = false
    private var estimatedDurationInMin: Int? = null
    private var createAt: LocalDateTime? = null
    private var updateAt: LocalDateTime? = null

    constructor(id: Long?, girlId: Long?, serviceName: String?, cost: Float?, isCostPerHour: Boolean?,
                create_at: LocalDateTime?, update_at: LocalDateTime?, estimatedDurationInMin: Int?){
        this.id = id
        this.girlId = girlId
        this.serviceName = serviceName
        this.cost = cost
        this.isCostPerMinute = isCostPerHour
        this.estimatedDurationInMin = estimatedDurationInMin
        this.createAt = create_at
        this.updateAt = update_at
    }


    fun getId(): Long? {
        return this.id
    }

    fun getGirlId(): Long? {
        return this.girlId
    }

    fun getServiceName(): String? {
        return this.serviceName
    }

    fun getCost(): Float? {
        return this.cost
    }

    fun getIsCostPerMinute(): Boolean? {
        return this.isCostPerMinute
    }

    fun getCreateAt(): LocalDateTime? {
        return this.createAt
    }

    fun getUpdateAt(): LocalDateTime? {
        return this.updateAt
    }

    fun getEstimatedDurationInMin(): Int? {
        return this.estimatedDurationInMin
    }
}