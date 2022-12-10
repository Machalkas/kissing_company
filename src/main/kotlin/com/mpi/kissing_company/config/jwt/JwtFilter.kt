package com.mpi.kissing_company.config.jwt

import com.mpi.kissing_company.config.UserSecurity
import com.mpi.kissing_company.entities.Users
import lombok.RequiredArgsConstructor
import lombok.extern.java.Log
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.GenericFilterBean
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
@Log
@RequiredArgsConstructor
class JwtFilter : GenericFilterBean() {
    private val jwtProvider: UserJwtProvider? = null

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse, filterChain: FilterChain) {
        logger.info("do filter...")
        val token = getTokenFromRequest(servletRequest as HttpServletRequest)
        try {
            if (token != null && jwtProvider!!.validateToken(token)) {
                val user: Users = jwtProvider.getUserFromToken(token)
                val userSecurity = UserSecurity.fromUserEntityToCustomUserDetails(user)
                val auth = UsernamePasswordAuthenticationToken(userSecurity, null, userSecurity.authorities)
                SecurityContextHolder.getContext().authentication = auth
            } else {
                (servletResponse as HttpServletResponse).status = HttpServletResponse.SC_UNAUTHORIZED
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        filterChain.doFilter(servletRequest, servletResponse)
    }

    private fun getTokenFromRequest(request: HttpServletRequest): String? {
        val bearer = request.getHeader(AUTHORIZATION)
        return if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            bearer.substring(7)
        } else null
    }

    companion object {
        const val AUTHORIZATION = "Authorization"
    }
}