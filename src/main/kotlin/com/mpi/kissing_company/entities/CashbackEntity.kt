package com.mpi.kissing_company.entities

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "cashback", uniqueConstraints=[UniqueConstraint(columnNames = ["user_fk"])])
class CashbackEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null
    @ManyToOne(optional = false, cascade = arrayOf(CascadeType.REMOVE))
    @JoinColumn(name="user_fk")
    private var user: User? = null
    private var slutCoins: Float? = null

//    constructor(){}
    constructor(user: User?, slutCoins: Float?){
        this.user = user
        this.slutCoins = slutCoins
    }

    fun getUser(): User?{
        return this.user
    }

    fun getId(): Long?{
        return this.id
    }

    fun getSlutCoins(): Float? {
        return this.slutCoins
    }


    fun setUser(user: User?){
        this.user = user
    }

    fun setSlutCoins(coins: Float?){
        this.slutCoins = coins
    }
}