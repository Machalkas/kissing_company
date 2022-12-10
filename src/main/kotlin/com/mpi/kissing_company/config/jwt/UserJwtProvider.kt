package com.mpi.kissing_company.config.jwt

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.mpi.kissing_company.entities.Users
import io.jsonwebtoken.*
import lombok.extern.java.Log
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.ZoneId
import java.util.*


@Component
@Log
class UserJwtProvider {
    @Value("$(jwt.secret)")
    private val jwtSecret: String? = null
    @Throws(JsonProcessingException::class)
    fun generateToken(user: Users?): String {
        val date = Date.from(LocalDate.now().plusDays(7).atStartOfDay(ZoneId.systemDefault()).toInstant())
        val objectMapper = ObjectMapper()
        return Jwts.builder()
            .setSubject(objectMapper.writeValueAsString(user))
            .setExpiration(date)
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact()
    }

    fun validateToken(token: String?): Boolean {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token)
            return true
        } catch (expEx: ExpiredJwtException) {
//            com.mpi.kissing_company.config.jwt.JwtProvider.log.severe("Token expired")
        } catch (unsEx: UnsupportedJwtException) {
//            com.mpi.kissing_company.config.jwt.JwtProvider.log.severe("Unsupported jwt")
        } catch (mjEx: MalformedJwtException) {
//            com.mpi.kissing_company.config.jwt.JwtProvider.log.severe("Malformed jwt")
        } catch (sEx: SignatureException) {
//            com.mpi.kissing_company.config.jwt.JwtProvider.log.severe("Invalid signature")
        } catch (e: Exception) {
//            com.mpi.kissing_company.config.jwt.JwtProvider.log.severe("Invalid token")
        }
        return false
    }

    @Throws(JsonProcessingException::class)
    fun getUserFromToken(token: String?): Users {
        val claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).body
        val objectMapper = ObjectMapper()
        return objectMapper.readValue(claims.subject, Users::class.java)
    }
}