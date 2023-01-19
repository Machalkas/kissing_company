package com.mpi.kissing_company.controllers

import com.mpi.kissing_company.dto.ServiceHistoryDetailsDto
import com.mpi.kissing_company.entities.CashbackEntity
import com.mpi.kissing_company.entities.PaymentTokens
import com.mpi.kissing_company.payment.PaymentSystem
import com.mpi.kissing_company.repositories.*
import com.mpi.kissing_company.utils.ServiceHistoryUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException


@RestController
internal class PaymentController(private val repository: PaymentInfoRepository,
                                 private val service_history_repository: ServiceHistoryRepository,
                                 private val cashback_repository: CashbackRepository) {

    @Autowired
    private val serviceHistoryUtils = ServiceHistoryUtils()
    @Autowired
    private var paymentSystem: PaymentSystem? = null


    @GetMapping("/payment/success")
    fun successPaymentHandler(@RequestParam("bill_id") billId: String): ServiceHistoryDetailsDto {
        val payment_info = repository.findByBillId(billId).get()
        if (payment_info.getIsPayd() == true){
            throw ResponseStatusException(HttpStatus.CONFLICT, "Already paid")
        }
        if (paymentSystem?.checkPayment(billId) == false) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "Not paid")
        }
        payment_info.setIsPayd(true)
        repository.save(payment_info)
        val service_history = payment_info.getServiceHistory()
        service_history?.setStatus("APPROVED")

        val user = service_history?.getClient()!!
        val cost = service_history!!.getTotalCost()
        var cashback: CashbackEntity
        try {
            cashback = cashback_repository.findByUser(user).get()
        } catch (ex: NoSuchElementException){
            cashback = cashback_repository.save(CashbackEntity(user, 0.0F))
        }
        if (cost != null) {
            cashback?.setSlutCoins(cashback.getSlutCoins()?.plus(cost*5/100))
        }
        return serviceHistoryUtils.mapToDetailDto(service_history_repository.save(service_history))

    }

    @GetMapping("/payment/get_link/{service_history_id}")
    fun createPayment(@RequestParam("coins_to_use") coins: Float?, @PathVariable service_history_id: Long): String?{
        val service_history = service_history_repository.findById(service_history_id).orElseThrow { ResponseStatusException(
            HttpStatus.NOT_FOUND, "ServiceHistory not found") }
        if (service_history!!.getStatus() != "CREATED"){
            throw ResponseStatusException(HttpStatus.CONFLICT, "Already paid")
        }
        if (coins == null){
            val payment_result = service_history?.getTotalCost()?.let { paymentSystem?.createPayment(it) }
            repository.save(PaymentTokens(payment_result?.get(1), service_history))
            return payment_result?.get(0)
        }
        val user = service_history?.getClient()!!
        var cashback: CashbackEntity
        try {
            cashback = cashback_repository.findByUser(user).get()
        } catch (ex: NoSuchElementException){
            cashback = cashback_repository.save(CashbackEntity(user, 0.0F))
        }
        var valid_coins_amount = coins
        if(service_history.getTotalCost()!! <= valid_coins_amount){
            valid_coins_amount = service_history.getTotalCost()!! - 1
        }
        if(cashback.getSlutCoins()!! < valid_coins_amount){
            valid_coins_amount = cashback.getSlutCoins()!!
        }
        cashback.setSlutCoins(cashback.getSlutCoins()!! - valid_coins_amount)
        cashback_repository.save(cashback)
        val new_cost = service_history?.getTotalCost()!! - valid_coins_amount
        val payment_result = new_cost.let { paymentSystem?.createPayment(it) }
        repository.save(PaymentTokens(payment_result?.get(1), service_history))
        return payment_result?.get(0)
    }
}