package com.mpi.kissing_company.entities

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor


@Data
@AllArgsConstructor
@NoArgsConstructor
class UserSave {
    fun getUsername(): String? {
        return username
    }

    fun getPassword(): String? {
        return password
    }

    private val username: String? = null
    private val password: String? = null
}
