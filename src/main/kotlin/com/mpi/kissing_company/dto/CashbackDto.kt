package com.mpi.kissing_company.dto

class CashbackDto {
    private var id: Long? = null
    private var userUsername: String? = null
    private var slutCoins: Float? = null

    constructor(id: Long?, userUsername: String?, slutCoins: Float?) {
        this.id = id
        this.userUsername = userUsername
        this.slutCoins = slutCoins
    }

    fun getSlutCoins(): Float?{
        return this.slutCoins
    }

    fun getUsername(): String?{
        return this.userUsername
    }

    fun getId(): Long?{
        return this.id
    }

    fun setUsername(username: String?){
        this.userUsername = username
    }
}