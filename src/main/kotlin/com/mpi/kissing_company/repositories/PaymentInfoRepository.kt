package com.mpi.kissing_company.repositories

import com.mpi.kissing_company.entities.PaymentTokens
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

internal interface PaymentInfoRepository : JpaRepository<PaymentTokens?, String>{
    fun findByBillId(username: String?): Optional<PaymentTokens?>
}