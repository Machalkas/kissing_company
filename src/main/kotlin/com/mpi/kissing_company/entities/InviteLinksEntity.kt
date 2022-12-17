package com.mpi.kissing_company.entities


import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "invite_links")
class InviteLinks{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var invite_token: String? = null
    private var expirty_dt: LocalDateTime? = null
    private var create_dt: LocalDateTime? = null

    @PrePersist
    protected fun onCreate() {
        create_dt = LocalDateTime.now()
        expirty_dt = LocalDateTime.now().plusDays(10)
        invite_token = getRandomString(10)
    }

    fun getinvate_token(): String?{
        return this.invite_token
    }

    fun getexpirty_dt(): LocalDateTime?{
        return this.expirty_dt
    }

    fun getcreate_dt(): LocalDateTime?{
        return this.create_dt
    }
}

fun getRandomString(length: Int) : String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}