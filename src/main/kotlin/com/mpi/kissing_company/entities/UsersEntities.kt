package com.mpi.kissing_company.entities
import javax.persistence.*


@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var first_name: String,
    var second_name: String,
    var nickname: String,
    @ManyToOne var role:  Roles

//    fun
)


@Entity
@Table(name = "roles")
class Roles(
    @Id @GeneratedValue var id: Long? = null,
    var name: String
)

@Entity
@Table(name = "girls")
class Girls(
    @Id @GeneratedValue var id: Long? = null,
    @ManyToOne var user: User,
    var location: String,
    var age: Int,
    var height: Float,
    var weight: Float,
    var nation: String,
    var telephone: String,
    var hair_color: String
)
