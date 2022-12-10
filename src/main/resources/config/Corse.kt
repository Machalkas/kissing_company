//package com.mpi.kissing_company.config
//
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
//import org.springframework.web.cors.CorsConfiguration
//import org.springframework.web.cors.CorsConfigurationSource
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource
//
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig: WebSecurityConfigurerAdapter() {
//
//    @Throws(Exception::class)
//    override protected fun configure(http: HttpSecurity){
//        http.cors().and()
//    }
//
//    @Bean
//    fun corsConfigurationSource(): CorsConfigurationSource {
//        var source = UrlBasedCorsConfigurationSource()
//        source.registerCorsConfiguration("/**", CorsConfiguration().applyPermitDefaultValues())
//        return source
//    }
//}