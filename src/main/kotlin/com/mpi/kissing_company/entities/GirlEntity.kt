package com.mpi.kissing_company.entities

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "girls", uniqueConstraints=[UniqueConstraint(columnNames = ["user_username", "nickname"])])
class Girl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null
    @ManyToOne(optional = false, cascade = arrayOf(CascadeType.REMOVE))
    @JoinColumn(name="user_username")
    private var user: User? = null
    private var location: String? = null
    private var age: Int? = null
    private var height: Float? = null
    private var weight: Float? = null
    private var nation: String? = null
    private var telephone: String? = null
    private var hair_color: String? = null
    private var nickname: String? = null

//    constructor(){}
    constructor(user: User?, location: String?, age: Int?, height: Float?,
                weight: Float?, nation: String?, telephone: String?, hair_color: String?, nickname: String?){
        this.user = user
        this.location = location
        this.age = age
        this.height = height
        this.weight = weight
        this.nation = nation
        this.telephone = telephone
        this.hair_color = hair_color
        this.nickname = nickname
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Girl) return  false
        val girl = other
        return (id == girl.id && telephone == girl.telephone)
    }

    override fun hashCode(): Int {
        return Objects.hash(telephone, nation, id, user)
    }

    override fun toString(): String {
        return "Defka{ id="+id+", user="+user+", location="+location+"}"
    }

    fun getLocation(): String?{
        return this.location
    }
    fun getAge(): Int?{
        return this.age
    }

    fun getHeight(): Float?{
        return this.height
    }
    fun getWeight(): Float?{
        return this.weight
    }
    fun getNation(): String?{
        return this.nation
    }

    fun getTelephone(): String?{
        return this.telephone
    }

    fun getHair_color(): String?{
        return this.hair_color
    }

    fun getUser(): User?{
        return this.user
    }

    fun getId(): Long?{
        return this.id
    }

    fun getNikname(): String? {
        return this.nickname
    }

    fun setLocation(location: String?){
        this.location = location
    }
    fun setAge(age: Int?){
        this.age = age
    }

    fun setHeight(height: Float?){
        this.height = height
    }
    fun setWeight(weight: Float?){
        this.weight = weight
    }
    fun setNation(nation: String?){
        this.nation = nation
    }

    fun setTelephone(telephone: String?){
        this.telephone = telephone
    }

    fun setHair_color(hair_color: String?){
        this.hair_color = hair_color
    }

    fun setUser(user: User?){
        this.user = user
    }

    fun setNikname(nikname: String?){
        this.nickname = nickname
    }
}