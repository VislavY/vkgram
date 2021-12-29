package me.vislavy.vkgram.core

interface EventHandler<T> {

    fun onEvent(event: T)
}