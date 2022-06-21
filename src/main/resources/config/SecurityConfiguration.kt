package config

import com.mpi.kissing_company.services.PostgresUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


public class SecurityConfiguration: WebSecurityConfigurerAdapter() {
    @Autowired
    var userDetailsService: PostgresUserDetailsService? = null

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .authorizeRequests().anyRequest().authenticated()
            .and().httpBasic()
            .and().sessionManagement().disable()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    @Throws(Exception::class)
    override fun configure(builder: AuthenticationManagerBuilder) {
        builder.userDetailsService(userDetailsService)
    }
}