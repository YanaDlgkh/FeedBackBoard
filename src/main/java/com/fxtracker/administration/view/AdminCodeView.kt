package com.fxtracker.administration.view

import com.fxtracker.entity.AccessCode
import com.fxtracker.repository.AccessCodeRepository
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.datetimepicker.DateTimePicker
import com.vaadin.flow.component.dialog.Dialog
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.html.H4
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import jakarta.annotation.security.RolesAllowed
import org.springframework.security.access.annotation.Secured


@Route("admin/codes")
@RolesAllowed("ADMIN")
@PageTitle("Управление кодами")

class AdminCodeView (
    private val accessCodeRepository: AccessCodeRepository
) : VerticalLayout(){

    init {

        val grid = Grid(AccessCode::class.java).apply {
            setItems(accessCodeRepository.findAll())
            removeColumnByKey("id")
        }


        val addButton = Button("Создать новый код "){
            val dialog = Dialog()
            val codeField = TextField("Код доступа")

            val expirationField = DateTimePicker("Срок действия")

            val saveB = Button("Сохранить"){
                val code = codeField.value.trim()
                val expires = expirationField.value

                if (code.isNotBlank()){
                    accessCodeRepository.save(
                        AccessCode(code = code, expiresAt = expires)
                    )
                    dialog.close()
                    grid.setItems(accessCodeRepository.findAll())
                }
            }

            dialog.add(VerticalLayout(codeField,expirationField,saveB))
            dialog.open()

        }
        add(H4("Коды доступа"),addButton,grid)

    }



}