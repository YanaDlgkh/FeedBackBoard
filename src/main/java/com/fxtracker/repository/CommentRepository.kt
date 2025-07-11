package com.fxtracker.repository

import com.fxtracker.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID


interface CommentRepository:JpaRepository<Comment,UUID> {
//    fun findAllByNote(noteId :UUID):List<Comment>

}