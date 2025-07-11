package com.fxtracker.views.dialog

import com.fxtracker.service.NoteService
import com.fxtracker.service.UserService
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.dialog.Dialog
import com.vaadin.flow.component.html.H4
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.radiobutton.RadioButtonGroup
import com.vaadin.flow.component.textfield.TextArea

/**
 * Диалог создания заметки
 */
class NoteDialog(
    private val userService: UserService,
    private val noteService: NoteService
) : Dialog() {

    init {
        width = "400px"

        val currentUser = userService.getCurrentUser()
        val existingNote = noteService.findTodayNoteByUser(currentUser.id)


        val moodSelector = RadioButtonGroup<String>().apply {
            label = "Настроение"

            setItems("Положительное","Нейтральное","Отрицательное")
            value = "Нейтральное"
        }

        val colorMap = mapOf(
            "Положительное" to "#a0bc8a",
            "Нейтральное"  to "#b4b3b1",
            "Отрицательное" to "#c26b6b"
        )

        if (existingNote == null) {


            val input = TextArea("Выша заметка на сегодня").apply {
                width = "100%"
                maxLength = 500
            }

            val submit = Button("Опубликовать").apply {
                addThemeVariants(ButtonVariant.LUMO_PRIMARY)

                addClickListener {
                    if (input.value.isNotBlank()) {
                        val mood = moodSelector.value ?: "Нейтральное"
                        val color = colorMap[mood] ?: "#a0bc8a"
                        noteService.createNote(currentUser.id!!, input.value,color)
                        UI.getCurrent().page.reload()
                    }
                }
            }

            val closeButton = Button(Icon(VaadinIcon.CLOSE)).apply {
                element.setAttribute("aria-label", "close")
                addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE)
                style.set("margin-right", "auto")
            }

            val label = H4("Оставьте свою заметку").apply {
            }

            closeButton.addClickListener {
                close()
            }

            val headerLayout = HorizontalLayout(label, closeButton).apply {
                isPadding = false
                isSpacing = false
                width = "100%"

                setFlexGrow(1.0, label)
                setFlexGrow(0.0, closeButton)
            }

            add(headerLayout, VerticalLayout(input, moodSelector, submit))

        } else {
            add(Span("Вы уже оставили заметку на сегодня."))
        }


    }


    fun openDialog() {
        open()
    }


}