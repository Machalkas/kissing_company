package com.mpi.kissing_company

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.data.annotation.Id
annotation class Entity
annotation class GeneratedValue
annotation class ManyToOne

@EnableAutoConfiguration
@Entity
@Table(name = "user")
class User(
    @Id @GeneratedValue var id: Long? = null,
    var first_name: String,
    var second_name: String,
    var nickname: String,
    @ManyToOne var role:  Roles
)

annotation class Table(val name: String)

@EnableAutoConfiguration
@Entity
class Roles(
    @Id @GeneratedValue var id: Long? = null,
    var name: String
)

@EnableAutoConfiguration
@Entity
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
