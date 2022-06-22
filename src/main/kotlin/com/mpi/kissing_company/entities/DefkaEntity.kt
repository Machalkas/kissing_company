package com.mpi.kissing_company.entities

import org.springframework.data.annotation.Id
import javax.persistence.*

@Entity
@Table(name = "girls")
internal class Defka{
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
    @ManyToOne(optional = false)
    var user: User? = null
    var location: String? = null
    var age: Int? = null
    var height: Float? = null
    var weight: Float? = null
    var nation: String? = null
    var telephone: String? = null
    var hair_color: String? = null
}