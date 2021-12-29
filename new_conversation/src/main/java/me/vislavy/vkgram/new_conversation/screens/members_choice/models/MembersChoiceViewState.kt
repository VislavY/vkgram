package me.vislavy.vkgram.new_conversation.screens.members_choice.models

import me.vislavy.vkgram.new_conversation.UserModel

sealed class MembersChoiceViewState {

    object Loading: MembersChoiceViewState()
    object Error: MembersChoiceViewState()
    data class Display(
        val items: List<UserModel>,
        val members: List<UserModel>,
        val fabVisible: Boolean = false
    ): MembersChoiceViewState()
}