package com.mpi.kissing_company.payment

import com.qiwi.billpayments.sdk.client.BillPaymentClient
import com.qiwi.billpayments.sdk.client.BillPaymentClientFactory
import com.qiwi.billpayments.sdk.model.MoneyAmount
import com.qiwi.billpayments.sdk.model.`in`.PaymentInfo
import java.math.BigDecimal
import java.util.*


class PaymentSystem {
    private var secretKey: String = ""
    private var publicKey: String = ""

    constructor(public_key: String, private_key: String){
        this.secretKey = "eyJ2ZXJzaW9uIjoiUDJQIiwiZGF0YSI6eyJwYXlpbl9tZXJjaGFudF9zaXRlX3VpZCI6IjJ5MTUyMS0wMCIsInVzZXJfaWQiOiI3OTE4NTYyMzg1MCIsInNlY3JldCI6IjQ4MDYwZTllMmI4NjFiODNlOGJiMjdmMGM4NDlkNWFkMzNjYjAxN2ZhMTIyNzA2YjM0NGFmM2EyOGRiOWViNDYifX0="
        this.publicKey = "48e7qUxn9T7RyYE1MVZswX1FRSbE6iyCj2gCRwwF3Dnh5XrasNTx3BGPiMsyXQFNKQhvukniQG8RTVhYm3iP48qvsZF5PioXs3m3bTezGnG8GH5PJSXUTS6M9D6RfBK6vxheb1aBqjNmhwJvHXHLm9vatKv3Mc8a4uyq8hUDf5MBXgFydVG6XZWrkiCKX"
    }

    fun createPayment(cost: Double): String {
        val client = BillPaymentClientFactory.createDefault(this.secretKey)
        val amount = MoneyAmount(
            BigDecimal.valueOf(cost),
            Currency.getInstance("RUB")
        )
        val billId = UUID.randomUUID().toString()
        val successUrl = "https://105a-78-106-207-230.eu.ngrok.io/api/payment/success?bill_id=$billId"
        return client.createPaymentForm(PaymentInfo(this.publicKey, amount, billId, successUrl))
    }
}