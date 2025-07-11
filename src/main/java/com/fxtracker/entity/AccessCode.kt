package com.fxtracker.entity

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*


@Entity
@Table(name = "access_code")
data class AccessCode(
    @Id
    var id: UUID = UUID.randomUUID(),

    @Column(unique = true)
    var code: String? = null,


    //срок давности кода
    var expiresAt: LocalDateTime? = null,

    var description: String? = null


    ) {



}