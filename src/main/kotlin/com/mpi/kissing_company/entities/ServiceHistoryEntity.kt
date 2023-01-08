package com.mpi.kissing_company.entities

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.*

val statuses_list = listOf("CREATED", "APPROVED", "CANCEL_BY_CLIENT", "CANCEL_BY_GIRL", "CANCEL_BY_ADMIN", "STARTED", "ENDED")

@Entity
@Table(name = "service_history", uniqueConstraints=[UniqueConstraint(columnNames = ["girl_fk", "start_datetime"])])
class ServiceHistory{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    @ManyToOne(optional = false, cascade = arrayOf(CascadeType.REMOVE))
    @JoinColumn(name="girl_fk")
    private var girl: Girl? = null
    @ManyToOne(optional = false, cascade = arrayOf(CascadeType.REMOVE))
    @JoinColumn(name="service_fk")
    private var service: PriceList? = null
    @ManyToOne(optional = false, cascade = arrayOf(CascadeType.REMOVE))
    @JoinColumn(name="user_fk")
    private var client: User? = null
    @Column(name = "start_datetime", nullable = false)
    private var startDt: LocalDateTime? = null
    @Column(name = "end_datetime", nullable = true)
    private var endDt: LocalDateTime? = null
    @Column(name = "total_cost", nullable = true)
    private var totalCost: Float? = null
    @Column(nullable = false)
    private var status: String? = null
    @Column(name = "create_at")
    var createAt: LocalDateTime? = null
    @Column(name = "update_at")
    var updateAt: LocalDateTime? = null

    @PrePersist
    protected fun onCreate() {
        createAt = LocalDateTime.now()
        updateAt = createAt
    }

    @PreUpdate
    protected fun onUpdate() {
        updateAt = LocalDateTime.now()
    }

    constructor(girl: Girl?, service: PriceList?, client: User?, startDt: LocalDateTime?, totalCost: Float?, status: String?) {
        if (status !in statuses_list){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Status not in "+ statuses_list)
        }
        this.girl = girl
        this.service = service
        this.client = client
        this.startDt = startDt
        this.totalCost = totalCost
        this.status = status
    }

    fun getGirl(): Girl? {
        return this.girl
    }
    fun getService(): PriceList? {
        return this.service
    }
    fun getClient(): User? {
        return this.client
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

    fun setEndDt(dt: LocalDateTime?) {
        this.endDt = dt
    }
    fun setTotalCost(cost: Float?) {
        this.totalCost = cost
    }
    fun setStatus(status: String?) {
        if (status !in statuses_list){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Status not in "+ statuses_list)
        }
        this.status = status
    }
}