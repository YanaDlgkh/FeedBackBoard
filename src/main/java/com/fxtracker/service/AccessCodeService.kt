package com.fxtracker.service

import com.fxtracker.repository.AccessCodeRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class AccessCodeService(
    private val accessCodeRepository: AccessCodeRepository
) {

    fun isValid(code:String):Boolean{
        val access = accessCodeRepository.findByCodeIgnoreCase(code)
        return access?.expiresAt == null || access.expiresAt!!.isAfter(LocalDateTime.now())
    }

}