package com.mpi.kissing_company.config.jwt

import com.mpi.kissing_company.entities.Users
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


class UserSecurity : UserDetails {
    private var username: String? = null
    private var password: String? = null
    private var grantedAuthorities: Collection<GrantedAuthority?>? = null
    override fun getAuthorities(): Collection<GrantedAuthority?> {
        return grantedAuthorities!!
    }

    override fun getPassword(): String {
        return password!!
    }

    override fun getUsername(): String {
        return username!!
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    companion object {
        fun fromUserEntityToCustomUserDetails(user: Users): UserSecurity {
            val userSecurity = UserSecurity()
            userSecurity.username = user.getUsername()
            userSecurity.password = user.getPassword()
            userSecurity.grantedAuthorities = listOf(SimpleGrantedAuthority(user.getRoleName()))
            return userSecurity
        }
    }
}