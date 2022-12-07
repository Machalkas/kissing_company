//package com.mpi.kissing_company.services
//import org.springframework.beans.factory.annotation.Autowired
//import com.mpi.kissing_company.entities.User as Users
//import com.mpi.kissing_company.repositories.UserRepository as UserRepository
//import org.springframework.security.core.authority.SimpleGrantedAuthority
//import org.springframework.security.core.userdetails.User
//import org.springframework.security.core.userdetails.UserDetails
//import org.springframework.security.core.userdetails.UserDetailsService
//import org.springframework.security.core.userdetails.UsernameNotFoundException
//import org.springframework.stereotype.Component
//import java.util.*
//
//@Component
//class CustomUserDetailsService : UserDetailsService {
//    @Autowired
//    private val repository: UserRepository? = null
//    @Throws(UsernameNotFoundException::class)
//    override fun loadUserByUsername(username: String): UserDetails {
//        val user: Users = repository?.findByUsername(username) ?: throw UsernameNotFoundException("User not found")
//        val authorities: List<SimpleGrantedAuthority> =
//            listOf(SimpleGrantedAuthority("user"))
//        print(user)
//        return User(user.getUsername(), user.getPassword(), authorities)
//    }
//}