package com.fxtracker.entity

import jakarta.persistence.*
import java.time.LocalDate
import java.util.UUID


@Entity
@Table(name = "users")
data class User(
    @Id
    var id : UUID = UUID.randomUUID(),
    @Column(nullable = false, unique = true)
    var permanentId: String = "",

    var currentAlias: String = "",
    var aliasDate :LocalDate = LocalDate.now()

)