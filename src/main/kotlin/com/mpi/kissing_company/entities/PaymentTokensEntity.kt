package com.mpi.kissing_company.entities

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.*


@Entity
@Table(name = "Payment_tokens", uniqueConstraints=[UniqueConstraint(columnNames = ["service_history"])])
class PaymentTokens{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null
    @Column(unique = true)
    private var billId: String? = null
    @Column(unique = true, length = 1000)
    private var paymentUrl: String? = null
    @ManyToOne(optional = false)
    @JoinColumn(name="service_history")
    private var serviceHistory: ServiceHistory? = null
    private var isPayd: Boolean? = false
    var createAt: LocalDateTime? = null

    @PrePersist
    protected fun onCreate() {
        createAt = LocalDateTime.now()
    }


    constructor(billId: String?, payment_url: String?, serviceHistory: ServiceHistory?) {
        this.billId = billId
        this.paymentUrl = payment_url
        this.serviceHistory = serviceHistory
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
    fun getServiceHistory(): ServiceHistory? {
        return this.serviceHistory
    }

    fun getIsPayd(): Boolean? {
        return this.isPayd
    }

    fun setIsPayd(is_payd: Boolean?){
        this.isPayd = is_payd
    }

}