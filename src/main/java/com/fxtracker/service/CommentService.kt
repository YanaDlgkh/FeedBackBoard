package com.fxtracker.service

import com.fxtracker.entity.Comment
import com.fxtracker.repository.CommentRepository
import com.fxtracker.repository.NoteRepository
import com.fxtracker.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID


@Service
class CommentService(
    private val userService: UserService,
    private val noteService: NoteService,
    private val noteRepository: NoteRepository,
    private val userRepository: UserRepository,
    private val commentRepository: CommentRepository
) {

    fun addComment(noteId: UUID, userId: UUID, content: String): Comment {

        val note = noteRepository.findById(noteId).orElseThrow()
        val user = userRepository.findById(userId).orElseThrow()

        val comment = Comment(
            note = note,
            author = user,
            content = content,
            createdAt = LocalDateTime.now()
        )
        return commentRepository.save(comment)

    }


//    fun getCommentsForNote(noteId: UUID): List<Comment> {
//
//        return commentRepository.findAllByNote(noteId)
//
//    }


}