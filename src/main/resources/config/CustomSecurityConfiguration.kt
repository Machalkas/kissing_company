package config

import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    @Bean
    fun userDetailsService(bCryptPasswordEncoder: BCryptPasswordEncoder?): UserDetailsService? {
        val manager = InMemoryUserDetailsManager()
        if (bCryptPasswordEncoder != null) {
            manager.createUser(
                User.withUsername("user")
                    .password(bCryptPasswordEncoder.encode("userPass"))
                    .roles("USER")
                    .build()
            )
        }
        if (bCryptPasswordEncoder != null) {
            manager.createUser(
                User.withUsername("admin")
                    .password(bCryptPasswordEncoder.encode("adminPass"))
                    .roles("USER", "ADMIN")
                    .build()
            )
        }
        return manager
    }

    @Bean
    @Throws(Exception::class)
    fun authManager(
        http: HttpSecurity,
        bCryptPasswordEncoder: BCryptPasswordEncoder?,
        userDetailService: UserDetailsService?
    ): AuthenticationManager? {
        return http.getSharedObject(AuthenticationManagerBuilder::class.java)
            .userDetailsService(userDetailsService(bCryptPasswordEncoder))
            .passwordEncoder(bCryptPasswordEncoder)
            .and()
            .build()
    }


    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        http.csrf()
            .disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.DELETE)
            .hasRole("ADMIN")
            .antMatchers("/admin/**")
            .hasAnyRole("ADMIN")
            .antMatchers("/user/**")
            .hasAnyRole("USER", "ADMIN")
            .antMatchers("/signup")
            .anonymous()
            .anyRequest()
            .authenticated()
            .and()
            .httpBasic()
            .and()
            .csrf().disable()
            .cors()
        return http.build()
    }

}