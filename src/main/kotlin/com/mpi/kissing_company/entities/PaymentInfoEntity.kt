package com.mpi.kissing_company.entities

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.*


@Entity
@Table(name = "Payment_info")
class PaymentInfo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null
    @Column(unique = true)
    private var billId: String? = null
    @ManyToOne(optional = false)
    private var serviceHistory: ServiceHistory? = null
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

}