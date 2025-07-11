package com.fxtracker.service

import com.fxtracker.entity.User
import com.fxtracker.repository.UserRepository
import com.fxtracker.util.AliasGenerator
import com.vaadin.flow.server.VaadinSession
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.UUID


@Service
open class UserService(
    private val userRepository: UserRepository
) {

    companion object{
        private const val SESSION_KEY = "users"
    }


    @Transactional
    open fun getCurrentUser():User{

        val session = VaadinSession.getCurrent()
        val cachedUser = session.getAttribute(SESSION_KEY) as User?

        if (cachedUser != null && cachedUser.aliasDate == LocalDate.now()) {
            return cachedUser
        }

        val permanentId = session.getAttribute("permanentId") as? String ?: run {
            val newId = UUID.randomUUID().toString()
            session.setAttribute("permanentId",newId)
            newId
        }

        val user = userRepository.findByPermanentId(permanentId).orElseGet {
            val newUser = User(
                id = UUID.randomUUID(),
                permanentId = permanentId,
                currentAlias = AliasGenerator.generateAlias()
            )
            userRepository.save(newUser)
        }

        if (user.aliasDate != LocalDate.now()){
            user.currentAlias = AliasGenerator.generateAlias()
            user.aliasDate = LocalDate.now()
            userRepository.save(user)

        }

        session.setAttribute(SESSION_KEY, user)
        return user

    }


//    fun getAlias(userId:Long): String{
//        return userRepository.findById(userId).orElseThrow().
//    }


}