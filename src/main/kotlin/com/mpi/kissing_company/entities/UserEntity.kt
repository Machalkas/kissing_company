package com.mpi.kissing_company.entities

import com.sun.corba.se.spi.ior.ObjectId
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "users")
class User {
    @Id
    @Column(nullable = false)
    private var username: String? = null
    private var first_name: String? = null
    private var second_name: String? = null
    //    @Column(nullable = false)
    private var password: String? = null
    @ManyToOne(optional = true)
    private var role: Role? = null

    constructor(){}
    constructor(username: String?, first_name: String?, second_name: String?, role: Role?, password: String?){
        this.username = username
        this.first_name = first_name
        this.second_name = second_name
        this.role = role
        this.password = password
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return  false
        val user = other
        return (username == user.username && first_name == user.first_name && second_name == user.second_name)
    }

    override fun hashCode(): Int {
        return Objects.hash(username, first_name, second_name, role)
    }

    override fun toString(): String {
        return "User{ username="+username+", first_name="+first_name+", second_name="+second_name+"}"
    }

    fun getUsername(): String?{
        return this.username
    }
    fun getPassword(): String?{
        return this.password
    }

    fun getFName(): String?{
        return this.first_name
    }
    fun getSName(): String?{
        return this.second_name
    }
    fun getRole(): Role?{
        return this.role
    }

    fun setUsername(username: String?){
        this.username = username
    }

    fun setPassword(password: String?){
        this.password = password
    }

    fun setFName(first_name: String?){
        this.first_name = first_name
    }

    fun setSName(second_name: String?){
        this.second_name = second_name
    }

    fun setRole(role: Role?){
        this.role = role
    }
}