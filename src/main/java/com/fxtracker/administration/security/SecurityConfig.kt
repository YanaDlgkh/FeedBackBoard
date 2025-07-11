package com.fxtracker.administration.security

import com.vaadin.flow.spring.security.VaadinWebSecurity
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
import org.springframework.security.web.util.matcher.AntPathRequestMatcher


@Configuration
@EnableWebSecurity
open class SecurityConfig(
    private val env :Environment
) :VaadinWebSecurity() {

    override fun configure(http: HttpSecurity) {


        http.csrf{it.disable()}
            .authorizeHttpRequests {
            it.requestMatchers(("/admin/**")).authenticated()
//                .requestMatchers("/login","anonymous-login").permitAll()
//                it.anyRequest().permitAll()

        }
            .formLogin{
                login -> login.loginPage("/admin/login")
                .permitAll()
            }

        super.configure(http)


    }


    @Bean
    open fun userDetailsService():UserDetailsService{
        val username = env.getProperty("admin.username") ?: "admin"


        val encoder = BCryptPasswordEncoder()
        val passwordHash = env.getProperty("admin.password")
            ?: throw IllegalStateException("Admin password not set")
        val encodedPassword = encoder.encode(passwordHash)

        val admin = User.withUsername(username)
            .password(encodedPassword)
            .roles("ADMIN")
            .build()

        println("Логин - ${admin.password}")

        return InMemoryUserDetailsManager(admin)
    }

    @Bean
    open fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    open fun successHandler(): AuthenticationSuccessHandler {
        return SavedRequestAwareAuthenticationSuccessHandler().apply {
            setTargetUrlParameter("redirect")
        }
    }




}