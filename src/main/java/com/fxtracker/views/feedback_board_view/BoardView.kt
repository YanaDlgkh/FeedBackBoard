package com.fxtracker.views.feedback_board_view

import com.fxtracker.components.DateNavigator
import com.fxtracker.entity.Note
import com.fxtracker.service.NoteService
import com.fxtracker.service.UserService
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.H3
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.TextArea
import com.vaadin.flow.router.Menu
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import org.vaadin.lineawesome.LineAwesomeIconUrl
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@PageTitle("Доска заметок")
@Route("")
@Menu(order = 0.0, icon = LineAwesomeIconUrl.VR_CARDBOARD_SOLID)
class BoardView(
    private val userService: UserService,
    private val noteService: NoteService
) : VerticalLayout() {

    init {
        val user = userService.getCurrentUser()
        add(H3("Привет. Это твой псевдоним на сегодня ${user.currentAlias}"))

        val grid = Div().apply {
            style["display"] = "grid"
            style["gridTemplateColumns"] = "repeat(auto-fit, minmax(300px, 1fr))"
            style["gap"] = "20px"
            style["marginTop"] = "20px"
            style["width"] = "100%"
        }
//        setWidthFull()
        isSpacing = true
        isPadding = true

        val dateNavigator = DateNavigator { selectedDate ->
            refreshNotes(selectedDate, grid)

        }

        val navigatorWrapper = HorizontalLayout(dateNavigator).apply {
            width = "100%"
            justifyContentMode = FlexComponent.JustifyContentMode.CENTER
            alignItems = FlexComponent.Alignment.CENTER
        }

        add(navigatorWrapper,grid)
        refreshNotes(dateNavigator.getDate(), grid)
    }

    fun refreshNotes(localDate: LocalDate,grid: Div){
        grid.removeAll()

        val notes = noteService.getNotesForDate(localDate)

        notes.forEach { note ->
            val alias = note.user.currentAlias
            val noteCard = Div().apply {
                style["background"] = note.moodColor
                style["boxShadow"] = "0 2px 6px rgba(0,0,0,0.1)"
                style["borderRadius"] = "12px"
                style["padding"] = "16px"
                style["minHeight"] = "120px"
                style["display"] = "flex"
                style["flexDirection"] = "column"
                style["gap"] = "10px"
            }

            noteCard.add(
                Span("$alias").apply {
                    style["color"] = "#666"
                    style["fontSize"] = "small"
                },
                Span(note.content).apply {
                    style["fontWeight"] = "500"
                    style["whiteSpace"] = "pre-wrap"
                }
            )

            //##c26b6b для красного
            //#a0bc8a
            //#b4b3b1

            grid.add(noteCard)

        }

    }


}

