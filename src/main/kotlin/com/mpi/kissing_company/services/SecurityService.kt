package com.mpi.kissing_company.services


interface SecurityService {
    val isAuthenticated: Boolean

    fun autoLogin(username: String?, password: String?)
}