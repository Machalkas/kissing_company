package com.mpi.kissing_company.entities
import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import lombok.ToString
import java.io.Serializable
import java.sql.Timestamp
import javax.persistence.*


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "users")
class Users(
    @field:Column(name = "username") private val username: String,
    @field:Column(name = "password") @field:JsonIgnore private val password: String
) : Serializable {
    fun getUsername(): String? {
        return username
    }

    fun getPassword(): String? {
        return password
    }

    fun getRole(): String? {
        return role?.name
    }

    fun setRole(userRole: Roles?) {
        role = userRole
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role", nullable = false)
    private var role: Roles? = null
}


//@Entity
//class Users(
//    @Id
//    @Column(nullable = false)
//    var username: String,
//    var first_name: String,
//    var second_name: String,
//    @Column(nullable = false)
//    var password: String,
//    @ManyToOne(optional = false) var role:  Roles
//)


@Entity
class Roles(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false)
    var name: String
)

@Entity
class Girls(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne(optional = false) var user: Users,
    var location: String,
    var age: Int,
    var height: Float,
    var weight: Float,
    var nation: String,
    var telephone: String,
    var hair_color: String
)

@Entity
class GirlsPhotos(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(unique = true, nullable = false)
    val photo: String,
    @ManyToOne(optional = false) val girl: Girls

)

@Entity
class Feedbacks(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne(optional = false) val user: Users,
    @ManyToOne(optional = false) val girl: Girls,
    @Column(columnDefinition = "numeric")
    var stars: Double,
    var comment: String
)

@Entity
class GirlsFeedbacks(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne(optional = false) val user: Users,
    @ManyToOne(optional = false) val girl: Girls,
    @Column(columnDefinition = "numeric")
    var stars: Double,
    var comment: String
)

@Entity
class PriceList(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne(optional = false) val girl: Girls,
    @Column(nullable = false)
    var service_name: String,
    @Column(columnDefinition = "numeric", nullable = false)
    var cost: Float,
    @Column(nullable = false)
    var is_cost_per_hour: Boolean
)

@Entity
class ServiceHistory(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne(optional = false) val girl: Girls,
    @ManyToOne(optional = false) val service: PriceList,
    @ManyToOne(optional = false) val client: Users,
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