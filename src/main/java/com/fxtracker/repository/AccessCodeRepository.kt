package com.fxtracker.repository

import com.fxtracker.entity.AccessCode
import org.springframework.data.jpa.repository.JpaRepository


interface AccessCodeRepository :JpaRepository<AccessCode,Long> {

    fun findByCodeIgnoreCase(code : String): AccessCode?

}