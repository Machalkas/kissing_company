package config
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.beans.factory.annotation.Autowired
import com.src.main.kotlin.services.CustomUserDetailsService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.beans
import org.springframework.http.HttpMethod
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import kotlin.Throws
import java.lang.Exception
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.core.userdetails.User
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.router

@SpringBootApplication
class SecurityApplication

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfiguration : WebSecurityConfigurerAdapter() {
    @Autowired
    private val userDetailsService: CustomUserDetailsService? = null
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Throws(Exception::class)
    override fun configure (http: HttpSecurity) {
        http
            .anonymous().and()
            .authorizeRequests().anyRequest().authenticated().and()
            .authorizeRequests()
                .antMatchers("/ping**")
                .permitAll()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .logoutUrl("/logout")
            .logoutSuccessUrl("/login");
//        http
//            .userDetailsService(userDetailsService)
//            .authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/api/auth/signup").permitAll()
//                .antMatchers("/**").permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//            .httpBasic()
//            .and()
//            .csrf().disable()
//            .cors();
//        http {
//            httpBasic {}
//            authorizeRequests {
//                authorize("/greetings/**", hasAuthority("ROLE_ADMIN"))
//                authorize("/**", permitAll)
//            }
//        }
    }

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder())
    }

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }
}

fun main(args: Array<String>){
    runApplication<SecurityApplication>(*args){
        addInitializers(beans {
            bean {
                fun user(user: String, pw: String, vararg roles: String) =
                    User.withDefaultPasswordEncoder().username(user).password(pw).roles(*roles).build()
                InMemoryUserDetailsManager(user("jlong", "pw", "USER"))
            }
            bean {
                router {
                    GET("/greetings") { request ->
                        request.principal().map { it.name }.map { ServerResponse.ok().body(mapOf("greeting" to "Hello, $it")) }.orElseGet { ServerResponse.badRequest().build() }
                    }
                }
            }
        })
    }
}