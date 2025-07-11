package com.fxtracker.repository

import com.fxtracker.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID


@Repository
interface UserRepository : JpaRepository<User,UUID> {

    fun findByPermanentId(permanentId:String):Optional<User>




}