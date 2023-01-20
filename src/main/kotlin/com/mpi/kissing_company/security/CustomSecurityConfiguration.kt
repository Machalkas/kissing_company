package com.mpi.kissing_company.security

import com.mpi.kissing_company.payment.PaymentSystem
import com.mpi.kissing_company.services.CustomUserDetailsService
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@Configuration
public class SecurityConfig {

//    @Bean
//    fun userDetailsService(bCryptPasswordEncoder: PasswordEncoder): UserDetailsService? {
//        val manager = InMemoryUserDetailsManager()
//        if (bCryptPasswordEncoder != null) {
//            manager.createUser(
//                User.withUsername("user")
//                    .password(bCryptPasswordEncoder.encode("userPass"))
//                    .roles("USER")
//                    .build()
//            )
//        }
//        if (bCryptPasswordEncoder != null) {
//            manager.createUser(
//                User.withUsername("admin")
//                    .password(bCryptPasswordEncoder.encode("adminPass"))
//                    .roles("USER", "ADMIN")
//                    .build()
//            )
//        }
//        return manager
//    }

    @Bean
    fun paymentSystem(): PaymentSystem? {
        val private = System.getenv("PAYMENT_PRIVATE_KEY")
        val public = System.getenv("PAYMENT_PUBLIC_KEY")
        val redirectUrl = System.getenv("REDIRECT_URL")
        return PaymentSystem(public, private, redirectUrl)
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource? {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("*")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
        configuration.allowedHeaders = listOf("authorization", "content-type", "x-auth-token")
        configuration.exposedHeaders = listOf("x-auth-token")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun corsConfigurer(): WebMvcConfigurer? {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/api/**").allowedOrigins("*")
            }
        }
    }

    @Bean
    @Throws(Exception::class)
    fun authManager(
        http: HttpSecurity,
        passwordEncoder: PasswordEncoder,
        userDetailService: UserDetailsService?
    ): AuthenticationManager? {
        return http.getSharedObject(AuthenticationManagerBuilder::class.java)
            .userDetailsService(CustomUserDetailsService())
            .passwordEncoder(passwordEncoder)
            .and()
            .build()
    }


    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        http.csrf()
            .disable()
            .authorizeRequests()
            .antMatchers("/swagger-ui/**").permitAll()
            .antMatchers(HttpMethod.DELETE).authenticated()
            .antMatchers(HttpMethod.PUT).authenticated()
            .antMatchers("/api/admin/**")
            .hasAnyRole("ADMIN")
            .antMatchers("/api/user/**")
            .hasAnyRole("USER", "ADMIN")
            .antMatchers(HttpMethod.POST, "/api/auth/registration").permitAll()
            .antMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
            .antMatchers(HttpMethod.GET, "/").permitAll()
            .antMatchers(HttpMethod.GET, "/manifest.json").permitAll()
            .antMatchers(HttpMethod.GET, "/service-worker.js").permitAll()
            .antMatchers(HttpMethod.GET, "/js/**").permitAll()
            .antMatchers(HttpMethod.GET, "/css/**").permitAll()
            .antMatchers(HttpMethod.GET, "/img/**").permitAll()
//            .antMatchers(HttpMethod.POST, "/payment/status").permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .httpBasic()
            .and()
            .cors()

        return http.build()
    }

}
@Configuration
@OpenAPIDefinition
class OpenApiConfig {

}
