package com.mpi.kissing_company.controllers

import com.mpi.kissing_company.entities.ServiceHistory
import com.mpi.kissing_company.payment.PaymentSystem
import com.mpi.kissing_company.repositories.*
import com.mpi.kissing_company.utils.ServiceHistoryUtils
import com.mpi.kissing_company.utils.UserUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
internal class PaymentController(private val repository: PaymentInfoRepository,
                                 private val service_history_repository: ServiceHistoryRepository) {

    @Autowired
    private val serviceHistoryUtils = ServiceHistoryUtils()

    @Autowired
    private val userUtils = UserUtils()

    @Autowired
    private var paymentSystem: PaymentSystem? = null


    @GetMapping("/payment/success")
    fun successPaymentHandler(@RequestParam("bill_id") billId: String): ServiceHistory {
        println("billid = $billId")
        val payment_info = repository.findByBillId(billId).get()
        val service_history = payment_info.getServiceHistory()
        service_history?.setStatus("APPROVED")
        return service_history_repository.save(service_history)

    }

//    @GetMapping("/payment/create")
//    fun createPayment(): String?{
//        val url = paymentSystem?.createPayment(1.0)
//        return url
//    }
}