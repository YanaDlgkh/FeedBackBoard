package com.fxtracker.administration.security

import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder

object SecurityUtils {
    fun isUserLoggedIn(): Boolean {
        val authentication = SecurityContextHolder.getContext().authentication
        return authentication != null &&
                authentication.isAuthenticated &&
                authentication !is AnonymousAuthenticationToken
    }
}