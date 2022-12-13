package com.mpi.kissing_company.entities
import java.sql.Timestamp
import javax.persistence.*






// TODO: добавить связь между feedbacks и service_history

@Entity
internal class GirlsPhotos(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(unique = true, nullable = false)
    val photo: String,
    @ManyToOne(optional = false) val girl: Girl

)

//
@Entity
class Feedbacks(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne(optional = false) val user: User,
    @ManyToOne(optional = false) val girl: Girl,
    @Column(columnDefinition = "numeric")
    var stars: Double,
    var comment: String
)

@Entity
internal class GirlsFeedbacks(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne(optional = false) val user: User,
    @ManyToOne(optional = false) val girl: Girl,
    @Column(columnDefinition = "numeric")
    var stars: Double,
    var comment: String
)

@Entity
internal class PriceList(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne(optional = false) val girl: Girl,
    @Column(nullable = false)
    var service_name: String,
    @Column(columnDefinition = "numeric", nullable = false)
    var cost: Float,
    @Column(nullable = false)
    var is_cost_per_hour: Boolean
)

@Entity
internal class ServiceHistory(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne(optional = false) val girl: Girl,
    @ManyToOne(optional = false) val service: PriceList,
    @ManyToOne(optional = false) val client: User,
    @Column(columnDefinition = "timestamp", nullable = false)
    var start_timestamp: Timestamp,
    @Column(columnDefinition = "timestamp")
    var end_timestamp: Timestamp? = null,
    @Column(columnDefinition = "numeric", nullable = false)
    var total_cost: Float
)

@Entity
class FellholeInfo(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var address: String,
    var logo: String,
    @Column(nullable = false)
    var name: String
)