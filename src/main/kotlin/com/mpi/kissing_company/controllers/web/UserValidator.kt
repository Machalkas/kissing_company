package com.mpi.kissing_company.controllers.web

import com.mpi.kissing_company.entities.User
import com.mpi.kissing_company.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.ValidationUtils
import org.springframework.validation.Validator
import java.time.LocalDate


@Component
class UserValidator : Validator {
    @Autowired
    private val userRepo: UserRepository? = null
    override fun supports(aClass: Class<*>): Boolean {
        return User::class.java == aClass
    }

    override fun validate(o: Any, errors: Errors) {
        val user: User = o as User
        val localDate = LocalDate.now()
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty")
//        if (user.getUsername().length < 6 || user.getUsername()?.length ?:  > 50) {
//            errors.rejectValue("login", "Size.userForm.login")
//        }
        if (user.getUsername()?.let { userRepo?.findByUsername(it) } != null) {
            errors.rejectValue("login", "Duplicate.userForm.login")
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty")
//        if (user.getPassword().length() < 3 || user.getPassword().length() > 32) {
//            errors.rejectValue("password", "Size.userForm.password")
//        }
        if (!user.getPassword().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm")
        }
        if (!user.getUsername().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm")
        }
//        if (user.getPassword().isAfter(localDate)) {
//            errors.rejectValue("birthDate", "Diff.userForm.birthDate")
//        }
    }
}