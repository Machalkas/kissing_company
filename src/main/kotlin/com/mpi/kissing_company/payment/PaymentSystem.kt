package com.mpi.kissing_company.payment

import com.qiwi.billpayments.sdk.client.BillPaymentClientFactory
import com.qiwi.billpayments.sdk.model.BillStatus
import com.qiwi.billpayments.sdk.model.MoneyAmount
import com.qiwi.billpayments.sdk.model.`in`.PaymentInfo
import java.math.BigDecimal
import java.util.*


class PaymentSystem {
    private var privateKey: String = ""
    private var publicKey: String = ""
    private var url: String = ""

    constructor(public_key: String, private_key: String, redirect_url: String){
        this.privateKey = private_key
        this.publicKey = public_key
        this.url = redirect_url
    }

    fun createPayment(cost: Float): List<String> {
        val client = BillPaymentClientFactory.createDefault(this.privateKey)
        val amount = MoneyAmount(
            BigDecimal.valueOf(cost.toDouble()),
            Currency.getInstance("RUB")
        )
        val billId = UUID.randomUUID().toString()
        val successUrl = "${url}/api/payment/success?bill_id=$billId"
        return listOf(client.createPaymentForm(PaymentInfo(this.publicKey, amount, billId, successUrl)), billId)
    }

    fun checkPayment(billId: String): Boolean{
        val client = BillPaymentClientFactory.createDefault(this.privateKey)
        try {
            val response = client.getBillInfo(billId)
            if(response.status.value == BillStatus.PAID){
                return true
            }
            return false
        }
        catch (e: Exception){
            return false
        }

    }
}