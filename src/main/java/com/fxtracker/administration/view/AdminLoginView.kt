package com.fxtracker.administration.view

import com.fxtracker.administration.security.SecurityUtils
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.login.LoginForm
import com.vaadin.flow.component.login.LoginI18n
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.*
import com.vaadin.flow.server.auth.AnonymousAllowed
import com.vaadin.flow.spring.annotation.UIScope
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component


@Route("admin/login", layout = LoginLayout::class)
@PageTitle("Админ - вход")
@AnonymousAllowed
class AdminLoginView :VerticalLayout() , BeforeEnterObserver {

    private var loginForm = LoginForm().apply {
        action = "admin/login"
        val i18n = createLoginI18n()
        setI18n(i18n)
    }

    init {
        if (SecurityUtils.isUserLoggedIn()) {
            UI.getCurrent().navigate("admin/codes")
        }

        if (SecurityUtils.isUserLoggedIn()){
            println("true")
        } else{
            println("false")
        }

        println("$SecurityUtils.isUserLoggedIn()")

        add(loginForm)
        setWidthFull()
        justifyContentMode = FlexComponent.JustifyContentMode.CENTER
        alignItems = FlexComponent.Alignment.CENTER
    }



    private fun createLoginI18n(): LoginI18n {
        val i18n = LoginI18n.createDefault()
        val i18nHeader = LoginI18n.Header()
        i18nHeader.title = "FeedBackBoardAdmin"
        i18nHeader.description = ""
        i18n.header = i18nHeader
        val i18nForm = i18n.form
        i18nForm.title = "Авторизация"
        i18nForm.username = "Имя пользователя"
        i18nForm.password = "Пароль"
        i18nForm.submit = "Войти"
        i18nForm.forgotPassword = null
        i18n.form = i18nForm
        val i18nErrorMessage = i18n.errorMessage
        i18nErrorMessage.title = "Ошибка входа"
        i18nErrorMessage.message = "Неправильный логин или пароль"
        i18nErrorMessage.password = "Необходимо указать пароль"
        i18nErrorMessage.username = "Необходимо указать имя пользователя"
        i18n.errorMessage = i18nErrorMessage
        i18n.additionalInformation = ""
        return i18n
    }


    override fun beforeEnter(event: BeforeEnterEvent) {
        val auth = SecurityContextHolder.getContext().authentication
        if (auth != null &&
            auth.isAuthenticated &&
            auth !is AnonymousAuthenticationToken
        ) {
            // Уже залогинен — перенаправляем
            event.forwardTo("admin/codes")
        }
    }



}

//@UIScope
class LoginLayout : VerticalLayout(), RouterLayout