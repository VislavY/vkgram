package ru.vyapps.vkgram.core

import android.content.Context
import javax.inject.Inject

class VkAccessToken @Inject constructor (
    private val context: Context
) {

    val accessToken: String
        get() {
            val preferences = context.getSharedPreferences(context.getString(R.string.pref_file_key), Context.MODE_PRIVATE)
            return preferences.getString(context.getString(R.string.access_token_pref_key), null)!!
        }
}