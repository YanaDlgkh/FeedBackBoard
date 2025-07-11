package com.fxtracker.entity

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID


@Entity
@Table(name = "notes")
data class Note(

    @Id
    var id : UUID = UUID.randomUUID(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user :User = User(),


    @Column(name = "note_date", nullable = false)
    var noteDate : LocalDate = LocalDate.now(),

    var content : String? = "",

    @Column(nullable = false)
    var moodColor :String? = "#b4b3b1",

    var createAt :LocalDateTime = LocalDateTime.now(),


    ){
}