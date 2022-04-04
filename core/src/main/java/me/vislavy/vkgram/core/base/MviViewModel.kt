package me.vislavy.vkgram.core.base

interface MviViewModel<Event> {
    fun onEvent(event: Event)
}