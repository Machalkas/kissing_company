package com.mpi.kissing_company.dto

import java.time.LocalDateTime

open class ServiceHistoryDto {
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
                totalCost: Float?, status: String?, endDt: LocalDateTime?, createAt: LocalDateTime?, updateAt: LocalDateTime?) {
        this.id = id
        this.girl_id = girl_id
        this.service_id = service_id
        this.user_username = user_username
        this.startDt = startDt
        this.totalCost = totalCost
        this.status = status
        this.endDt = endDt
        this.createAt = createAt
        this.updateAt = updateAt
    }

    fun getId(): Long? {
        return this.id
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
    fun getCreateAt(): LocalDateTime? {
        return this.createAt
    }
    fun getUpdateAt(): LocalDateTime? {
        return this.updateAt
    }

    fun setUsername(username: String?){
        this.user_username = username
    }
    fun setStatus(status: String?){
        this.status = status?.uppercase()
    }
    fun setEndDt(dt: LocalDateTime?){
        this.endDt = dt
    }
    fun setTotalCost(cost: Float?){
        this.totalCost = cost
    }
    fun setGirlId(id: Long?){
        this.girl_id = id
    }
}

class ServiceHistoryDetailsDto(
    id: Long?,
    girl_id: Long?,
    service_id: Long?,
    user_username: String?,
    startDt: LocalDateTime?,
    totalCost: Float?,
    status: String?,
    endDt: LocalDateTime?,
    createAt: LocalDateTime?,
    updateAt: LocalDateTime?
) : ServiceHistoryDto(id, girl_id, service_id, user_username, startDt, totalCost, status, endDt, createAt, updateAt) {

    private var clientDto: UserInfoDto? = null
    private var girlDto: GirlDto? = null
    private var priceListDto: PriceListDto? = null
    private var paymentTokensDto: PaymentTokensDto? = null

    fun getClientDto(): UserInfoDto?{
        return this.clientDto
    }
    fun getGirlDto(): GirlDto?{
        return this.girlDto
    }
    fun getPriceListDto(): PriceListDto?{
        return this.priceListDto
    }
    fun getPaymentTokensDto(): PaymentTokensDto?{
        return this.paymentTokensDto
    }

    fun setClientDto(dto: UserInfoDto){
        this.clientDto = dto
    }
    fun setGirlDto(dto: GirlDto?){
        this.girlDto = dto
    }
    fun setPriceListDto(dto: PriceListDto?){
        this.priceListDto = dto
    }
    fun setPaymentTokensDto(dto: PaymentTokensDto?){
        this.paymentTokensDto = dto
    }
}