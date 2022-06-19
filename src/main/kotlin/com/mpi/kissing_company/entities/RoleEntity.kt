package com.mpi.kissing_company.entities

import javax.persistence.*

@Entity
@Table(name = "roles")
internal class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
    @Column(nullable = false)
    lateinit var name: String

    constructor(){}
    constructor(name: String){
        this.name = name
    }

    override fun toString(): String {
        return "User{ id="+id+", name="+name+"}"
    }
    //    override fun equals(other: Any?): Boolean {
//        if (this === other) return true
//        if (other !is User) return false
//        val role = other
//        return (name == role.name && id == role.id)
//    }
}
