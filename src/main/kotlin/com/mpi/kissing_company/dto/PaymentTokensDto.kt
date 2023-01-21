package com.mpi.kissing_company.dto

import com.mpi.kissing_company.entities.ServiceHistory
import lombok.Data
import java.time.LocalDateTime
import javax.persistence.ManyToOne

@Data
class PaymentTokensDto {
    private var id: Long? = null
    private var billId: String? = null
    private var paymentUrl: String? = null
    private var serviceHistoryId: Long? = null
    private var isPayd: Boolean? = false
    private var createAt: LocalDateTime? = null

    constructor(id: Long?, billId: String?, paymentUrl: String?, serviceHistoryId: Long?, isPayd: Boolean?, createAt: LocalDateTime?){
        this.id = id
        this.billId = billId
        this.paymentUrl = paymentUrl
        this.serviceHistoryId = serviceHistoryId
        this.isPayd = isPayd
        this.createAt = createAt
    }

    fun getId(): Long? {
        return this.id
    }
    fun getBillId(): String? {
        return this.billId
    }
    fun getPaymentUrl(): String?{
        return this.paymentUrl
    }
    fun getServiceHistoryId(): Long? {
        return this.serviceHistoryId
    }
    fun getIsPayd(): Boolean? {
        return this.isPayd
    }
}