package com.mpi.kissing_company.payment

import com.qiwi.billpayments.sdk.client.BillPaymentClient
import com.qiwi.billpayments.sdk.client.BillPaymentClientFactory
import com.qiwi.billpayments.sdk.model.MoneyAmount
import com.qiwi.billpayments.sdk.model.`in`.PaymentInfo
import java.math.BigDecimal
import java.sql.DriverManager.println
import java.util.*

class PaymentSystem {
    var paymentUrl: String?=null
    constructor() {
        val secretKey =
            ""
        val client = BillPaymentClientFactory.createDefault(secretKey)
        val publicKey =
            ""
        val amount = MoneyAmount(
            BigDecimal.valueOf(20),
            Currency.getInstance("RUB")
        )
        val billId = UUID.randomUUID().toString()
        val successUrl = "https://papabar.ru/priority-success"
        this.paymentUrl = client.createPaymentForm(PaymentInfo(publicKey, amount, billId, successUrl))
    }
}