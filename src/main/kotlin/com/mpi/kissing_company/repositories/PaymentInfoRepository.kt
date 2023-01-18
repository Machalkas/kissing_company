package com.mpi.kissing_company.repositories

import com.mpi.kissing_company.entities.PaymentInfo
import com.mpi.kissing_company.entities.Role
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
import com.mpi.kissing_company.entities.User as Users

internal interface PaymentInfoRepository : JpaRepository<PaymentInfo?, String>{
    fun findByBillId(username: String?): Optional<PaymentInfo?>
}