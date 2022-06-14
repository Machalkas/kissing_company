package com.mpi.kissing_company.services

import com.mpi.kissing_company.entities.Roles
import com.mpi.kissing_company.entities.UserSave
import com.mpi.kissing_company.entities.Users
import com.mpi.kissing_company.repositories.RoleRepository
import com.mpi.kissing_company.repositories.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.Sort
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*


@Service
@RequiredArgsConstructor
class UserService {
    private val userRepository: UserRepository? = null
    private val roleRepository: RoleRepository? = null
    private val passwordEncoder: PasswordEncoder? = null


    fun findAllAndSort(): List<Users?> {
        return userRepository!!.findAll(
            Sort.by(
                Sort.Order.asc("username"),
                Sort.Order.desc("username")
            )
        )
    }

    fun findOneUser(username: String): Users? {
        return userRepository?.findByUsername(username)
    }

    fun findOneUsername(username: String?): Users? {
        return userRepository?.findByUsername(username)
    }

    fun saveUser(user: UserSave): Users? {
        if (userRepository?.findByUsername(user.getUsername()) == null) {
            val userRole: Roles? = roleRepository?.findRoleById(2L)
            val newUser = Users(user.getUsername()!!, passwordEncoder!!.encode(user.getPassword()))
            newUser.setRole(userRole)
            return userRepository!!.save(newUser)
        }
        return null
    }

    fun findByRoleName(name: String?): List<Users?>? {
        return userRepository?.findByRoleName(name)
    }
}