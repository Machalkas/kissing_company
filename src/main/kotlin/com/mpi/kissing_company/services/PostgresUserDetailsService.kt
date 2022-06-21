package com.mpi.kissing_company.services

import com.mpi.kissing_company.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import com.mpi.kissing_company.entities.User as Users

@Component
abstract class PostgresUserDetailsService: UserDetailsService {
    @Autowired
    private var repository: UserRepository? = null

//    public fun get(usernsme: String){
//        return repository.findByUsername(usernsme)
//    }
    override fun loadUserByUsername(username: String): UserDetails {
        var user:Users? = repository?.findByUsername(username)

        if (user == null){
            throw UsernameNotFoundException("User not found")
        }
        var authorities: List<SimpleGrantedAuthority> = listOf(SimpleGrantedAuthority("user"))
//        return User(user.getUsername())
        return User(user.getUsername(), user.getPassword(), authorities)
    }

    fun create(user: Users): Users? {
        return repository?.save(user)
    }


}

