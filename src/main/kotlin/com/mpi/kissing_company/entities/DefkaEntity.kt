package com.mpi.kissing_company.entities

import org.springframework.data.annotation.Id
import javax.persistence.*

@Entity
@Table(name = "girls")
internal class Defka{
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    @ManyToOne(optional = false)
    var user: User? = null
    var location: String? = null
    var age: Int? = null
    var height: Float? = null
    var weight: Float? = null
    var nation: String? = null
    var telephone: String? = null
    var hair_color: String? = null

    constructor(){}
    constructor(id: Long?, user: User?, location: String?, age: Int?, height: Float?, weight: Float?, nation: String?, telephone: String?, hair_color: String?){
        this.id = id
        this.user = user
        this.location = location
        this.age = age
        this.height = height
        this.weight = weight
        this.nation = nation
        this.telephone = telephone
        this.hair_color = hair_color
    }

    override fun toString(): String {
        return "Defka{ id="+id+", user="+user+", location="+location+"}"
    }
}