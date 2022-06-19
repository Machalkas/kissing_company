package com.mpi.kissing_company.entities

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "users")
internal class User {
    @Id
    @Column(nullable = false)
    var username: String? = null
    var first_name: String? = null
    var second_name: String? = null
    @Column(nullable = false)
    var password: String? = null
    @ManyToOne(optional = false)
    var role: Roles? = null

    constructor(){}
    constructor(username: String?, first_name: String?, second_name: String?, role: Roles?, password: String?){
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
}


//@Entity
//internal class Employee {
//    @Id
//    @GeneratedValue
//    var id: Long? = null
//    var name: String? = null
//    var role: String? = null
//
//    constructor() {}
//    constructor(name: String?, role: String?) {
//        this.name = name
//        this.role = role
//    }
//
//    override fun equals(o: Any?): Boolean {
//        if (this === o) return true
//        if (o !is Employee) return false
//        val employee = o
//        return (id == employee.id && name == employee.name
//                && role == employee.role)
//    }
//
//    override fun hashCode(): Int {
//        return Objects.hash(id, name, role)
//    }
//
//    override fun toString(): String {
//        return "Employee{" + "id=" + id + ", name='" + name + '\'' + ", role='" + role + '\'' + '}'
//    }
//}