package com.fxtracker.repository

import com.fxtracker.entity.Note
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.util.UUID

interface NoteRepository :JpaRepository<Note,UUID>{

    fun findAllByNoteDate(date: LocalDate):List<Note>

    fun findByUserIdAndNoteDate(userId:UUID,date: LocalDate):Note?

    fun existsByUserIdAndNoteDate(userId: UUID,date: LocalDate):Boolean


}