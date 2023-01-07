package com.mpi.kissing_company.entities

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "price_list")
class PriceList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    @ManyToOne(optional = false, cascade = arrayOf(CascadeType.REMOVE))
    var girl: Girl? = null
    @Column(name="service_name", nullable = false)
    var serviceName: String? = null
    @Column(columnDefinition = "numeric", nullable = false)
    var cost: Float? = null
    @Column(name="is_cost_per_hour", nullable = false)
    var isCostPerHour: Boolean? = false
    var create_at: LocalDateTime? = null
    var update_at: LocalDateTime? = null

    @PrePersist
    protected fun onCreate() {
        create_at = LocalDateTime.now()
        update_at = create_at
    }

    @PreUpdate
    protected fun onUpdate() {
        update_at = LocalDateTime.now()
    }

//    constructor(){}
    constructor(girl: Girl?, serviceName: String?, cost: Float?, isCostPerHour: Boolean?){
        this.girl = girl
        this.serviceName = serviceName
        this.cost = cost
        this.isCostPerHour = isCostPerHour
    }



//    fun getId(): Long? {
//        return this.id
//    }
//
//    fun getGirl(): Girl? {
//        return this.girl
//    }
//
//    fun getServiceName(): String? {
//        return this.serviceName
//    }
//
//    fun getCost(): Float? {
//        return this.cost
//    }
//
//    fun getIsCostPerHour(): Boolean? {
//        return this.isCostPerHour
//    }
}