package com.mpi.kissing_company.entities

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.*


@Entity
@Table(name = "Payment_tokens")
class PaymentTokens{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null
    @Column(unique = true)
    private var billId: String? = null
    @ManyToOne(optional = false)
    private var serviceHistory: ServiceHistory? = null
    private var isPayd: Boolean? = false
    var createAt: LocalDateTime? = null

    @PrePersist
    protected fun onCreate() {
        createAt = LocalDateTime.now()
    }


    constructor(billId: String?, serviceHistory: ServiceHistory?) {
        this.billId = billId
        this.serviceHistory = serviceHistory
    }

    fun getId(): Long? {
        return this.id
    }
    fun getDillId(): String? {
        return this.billId
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