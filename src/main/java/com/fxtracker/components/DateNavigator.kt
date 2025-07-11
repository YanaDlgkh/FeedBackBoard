package com.fxtracker.components

import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.datepicker.DatePicker
import com.vaadin.flow.component.html.H4
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.spring.annotation.UIScope
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@UIScope
class DateNavigator(
    private val onDataChanged: (LocalDate) -> Unit
) : VerticalLayout() {

    private var currentDate: LocalDate = LocalDate.now()

    private val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    private val dateLabel = H4(currentDate.format(formatter))

    private val datePicker = DatePicker().apply {
        value = currentDate
        locale = Locale("ru", "RU")
        isClearButtonVisible = true
    }


    init {
//        isSpacing = false
//        isPadding = false


        val prevButton = Button("←").apply {
            addClickListener {
                currentDate = currentDate.minusDays(1)
                update()
            }
        }

        val nextButton = Button("→").apply {
            addClickListener {
                currentDate = currentDate.plusDays(1)
                update()
            }
        }

        val header = HorizontalLayout(prevButton, datePicker, nextButton).apply {
            alignItems = FlexComponent.Alignment.BASELINE
            isSpacing = true
        }

        datePicker.addValueChangeListener { event ->
            if (event.value != null) {
                currentDate = event.value
                update()
            }
        }


        add(header)

    }

    private fun update(){
        dateLabel.text = currentDate.format(formatter)
        datePicker.value = currentDate
        onDataChanged(currentDate)
    }

    fun getDate():LocalDate = currentDate


}