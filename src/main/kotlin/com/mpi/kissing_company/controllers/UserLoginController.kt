package com.mpi.kissing_company.controllers

import com.mpi.kissing_company.controllers.web.UserValidator
import com.mpi.kissing_company.entities.User
import com.mpi.kissing_company.repositories.RoleRepository
import com.mpi.kissing_company.repositories.UserRepository
import com.mpi.kissing_company.services.PostgresUserDetailsService
import com.mpi.kissing_company.services.SecurityService

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.util.EnumSet.of
import java.util.OptionalInt.of


@Controller
class UsersLoginController {
    @Autowired
    private val securityService: SecurityService? = null

    @Autowired
    private val userRepo: UserRepository? = null

    @Autowired
    private val RolesRepo: RoleRepository? = null

    @Autowired
    private val userService: PostgresUserDetailsService? = null

    @Autowired
    private val userValidator: UserValidator? = null
    @GetMapping("/registration")
    fun registration(model: Model): String {
        if (securityService?.isAuthenticated == true) {
            return "redirect:/profile"
        }
        model.addAttribute("userForm", User())
        return "registration"
    }

    @PostMapping("/registration")
    fun registration(@ModelAttribute("userForm") userForm: User, bindingResult: BindingResult): String {
        userValidator?.validate(userForm, bindingResult)
        if (bindingResult.hasErrors()) {
            return "registration"
        }
        val user: User = userForm
        userService?.create(userForm)
        securityService?.autoLogin(userForm.getUsername(), userForm.getPassword())
        return "redirect:/profile"
    }

    @GetMapping("/login")
    fun login(model: Model, error: String?, logout: String?): String {
        if (error != null) {
            model.addAttribute("error", "Your username and password is invalid.")
            return "login"
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.")
            return "login"
        }
        return if (securityService?.isAuthenticated == true) {
            "redirect:/profile"
        } else "login"
    }

//    @GetMapping("/profile")
//    fun welcome(model: Model): String {
//        val auth: Authentication = SecurityContextHolder.getContext().authentication
//        val user: User = userRepo?.findByUsername(auth.getName()) ?:
////        System.out.println(user)
////        val date: List<Int> = java.util.List.of(0, 0, 0, 0, 0, 0, 1, 2, 0, 0, 0, 0)
////        val jobs: List<Int> = ArrayList()
//        for (i in 1..12) {
//            System.out.println(user.getId())
//            println(i)
//            val j: Long = user.getId()
//            val k = i.toLong()
//            val value = i.toString() + ""
//            //            List<Integer> temp = jobRepo.findByAdventurerAndDate(j, k);
////            jobs.add(temp.size());
//        }
//        println(jobs)
//        model.addAttribute("data", date)
//        model.addAttribute("user", user)
//        return "profileDemo"
//    }
}