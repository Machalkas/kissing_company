package com.mpi.kissing_company.entities


import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "invite_links")
class InviteLinks{
    @Id
    @Column(name="invite_token")
    private var inviteToken: String? = java.util.UUID.randomUUID().toString()
    @Column(name="expirty_dt")
    private var expiryDt: LocalDateTime? = null
    @Column(name="create_dt")
    private var createDt: LocalDateTime? = null

    @PrePersist
    protected fun onCreate() {
        createDt = LocalDateTime.now()
        expiryDt = LocalDateTime.now().plusDays(10)
    }

    fun getinvateToken(): String?{
        return this.inviteToken
    }

    fun getexpirtyDt(): LocalDateTime?{
        return this.expiryDt
    }

    fun getcreateDt(): LocalDateTime?{
        return this.createDt
    }
}
