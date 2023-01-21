package com.mpi.kissing_company.repositories

import com.mpi.kissing_company.entities.PaymentTokens
import com.mpi.kissing_company.entities.ServiceHistory
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

internal interface PaymentTokensRepository : JpaRepository<PaymentTokens?, String>{
    fun findByBillId(username: String?): Optional<PaymentTokens?>
    fun findByserviceHistory(serviceHistory: ServiceHistory?): PaymentTokens?
}