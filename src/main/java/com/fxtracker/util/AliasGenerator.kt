package com.fxtracker.util

object AliasGenerator {

    private val adjectives = listOf("Blue","Red","Silent","Sharp","Happy")
    private val animals = listOf("Cat","Fox","Rabbit","Hawk")


    fun generateAlias():String{
        return "${adjectives.random()}${animals.random()}${(100..999).random()}"
    }





}