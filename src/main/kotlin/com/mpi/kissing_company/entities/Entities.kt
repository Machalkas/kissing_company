package com.mpi.kissing_company.entities
import java.sql.Timestamp
import java.time.LocalDateTime
import javax.persistence.*






// TODO: добавить связь между feedbacks и service_history

//
@Entity
class Feedbacks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
    @ManyToOne(optional = false)
    var user: User? = null

    @ManyToOne(optional = false, cascade = arrayOf(CascadeType.REMOVE))
    var girl: Girl? = null

    @Column(columnDefinition = "numeric")
    var stars: Double? = null
    var comment: String? = null

    var createAt: LocalDateTime? = null
    @Column(name = "update_at")
    var updateAt: LocalDateTime? = null

    @PrePersist
    protected fun onCreate() {
        createAt = LocalDateTime.now()
        updateAt = createAt
    }

    @PreUpdate
    protected fun onUpdate() {
        updateAt = LocalDateTime.now()
    }

    constructor(user: User?, girl: Girl?, stars: Double?, comment: String?){
        this.user = user
        this.girl = girl
        this.comment = comment
        this.stars = stars
    }
}

@Entity
@Table(name = "app_feedbacks")
class AppFeedbacks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
    @ManyToOne(optional = false)
    var user: User? = null

    @Column(columnDefinition = "numeric")
    var stars: Double? = null
    var comment: String? = null

    var createAt: LocalDateTime? = null
    @Column(name = "update_at")
    var updateAt: LocalDateTime? = null

    @PrePersist
    protected fun onCreate() {
        createAt = LocalDateTime.now()
        updateAt = createAt
    }

    @PreUpdate
    protected fun onUpdate() {
        updateAt = LocalDateTime.now()
    }

    constructor(user: User?, stars: Double?, comment: String?){
        this.user = user
        this.comment = comment
        this.stars = stars
    }
}




//@Entity
//internal class ServiceHistory(
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    val id: Long? = null,
//    @ManyToOne(optional = false) val girl: Girl,
//    @ManyToOne(optional = false) val service: PriceList,
//    @ManyToOne(optional = false) val client: User,
//    @Column(columnDefinition = "timestamp", nullable = false)
//    var start_timestamp: Timestamp,
//    @Column(columnDefinition = "timestamp")
//    var end_timestamp: Timestamp? = null,
//    @Column(columnDefinition = "numeric", nullable = false)
//    var total_cost: Float
//)

@Entity
class FellholeInfo(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var address: String,
    var logo: String,
    @Column(nullable = false)
    var name: String
)