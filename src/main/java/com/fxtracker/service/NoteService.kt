package com.fxtracker.service

import com.fxtracker.entity.Note
import com.fxtracker.repository.NoteRepository
import com.fxtracker.repository.UserRepository
import com.vaadin.flow.component.notification.Notification
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID


@Service
class NoteService (
    private val noteRepository: NoteRepository,
    private val userRepository: UserRepository
){

    fun createNote(userId:UUID,content:String,moodColor: String):Note{

        val user = userRepository.findById(userId)

        if (noteRepository.existsByUserIdAndNoteDate(userId, LocalDate.now())){
            throw IllegalStateException("Заметка существует")
            Notification.show("Заметка на сегодняшний день уже существует")
        }

        val note = Note(
            user = user.get(),
            content = content,
            noteDate = LocalDate.now(),
            createAt = LocalDateTime.now(),
            moodColor = moodColor
        )
        return noteRepository.save(note)

    }


    fun findTodayNoteByUser(userId: UUID):Note?{
        return noteRepository.findByUserIdAndNoteDate(userId, LocalDate.now())
    }

    fun getNotesForDate(date: LocalDate):List<Note>{
        return noteRepository.findAllByNoteDate(date)
    }


}