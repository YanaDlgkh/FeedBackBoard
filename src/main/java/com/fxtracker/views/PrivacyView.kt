package com.fxtracker.views

import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.html.H3
import com.vaadin.flow.component.html.Paragraph
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.Menu
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.server.VaadinSession
import org.vaadin.lineawesome.LineAwesomeIconUrl


@PageTitle("Конфиденциальность")
@Route("privacy")
@Menu(order = 1.0, icon = LineAwesomeIconUrl.NAPSTER)
class PrivacyView : VerticalLayout() {

    init {

        setSizeFull()
        isPadding = true
        isSpacing = true

        add(
        Paragraph("Здесь вы можете сбросить свою анонимную личность. Это удалит ваш псевдоним и историю."),

        Button("Сбросить личность", Icon(VaadinIcon.TRASH)).apply {

            addClickListener {
                val session = VaadinSession.getCurrent()
                session.setAttribute("permanentId",null)
                session.setAttribute("user",null)
                session.setAttribute("SESSION_KEY",null)
                session.close()

                UI.getCurrent().page.setLocation("/")

            }

        }
        )


    }



}