package me.vislavy.vkgram.core

interface IntentHandler<T> {

    fun onIntent(intent: T)
}