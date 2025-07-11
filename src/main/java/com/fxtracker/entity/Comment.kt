package com.fxtracker.entity

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.UUID


@Entity
@Table(name = "comments")
data class Comment(
    @Id
    var id: UUID = UUID.randomUUID(),

    @Column(columnDefinition = "TEXT")
    var content: String = "",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    var author: User = User(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_id", nullable = false)
    var note: Note = Note(),

    var createdAt: LocalDateTime = LocalDateTime.now()

) {
}