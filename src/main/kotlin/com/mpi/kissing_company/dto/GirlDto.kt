package com.mpi.kissing_company.dto

class GirlDto {
    private var id: Long? = null
    private var userUsername: String? = null
    private var location: String? = null
    private var age: Int? = null
    private var height: Float? = null
    private var weight: Float? = null
    private var nation: String? = null
    private var telephone: String? = null
    private var hair_color: String? = null
    private var nickname: String? = null

    constructor(id: Long?, userUsername: String?, location: String?, age: Int?, height: Float?,
                weight: Float?, nation: String?, telephone: String?, hair_color: String?, nickname: String?){
        this.id = id
        this.userUsername = userUsername
        this.location = location
        this.age = age
        this.height = height
        this.weight = weight
        this.nation = nation
        this.telephone = telephone
        this.hair_color = hair_color
        this.nickname = nickname
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

    fun getUsername(): String?{
        return this.userUsername
    }

    fun getNikname(): String?{
        return this.nickname
    }

    fun getId(): Long?{
        return this.id
    }

    fun setUsername(username: String?){
        this.userUsername = username
    }
}