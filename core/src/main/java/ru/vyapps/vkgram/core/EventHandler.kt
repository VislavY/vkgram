package ru.vyapps.vkgram.core

interface EventHandler<T> {

    fun onEvent(event: T)
}