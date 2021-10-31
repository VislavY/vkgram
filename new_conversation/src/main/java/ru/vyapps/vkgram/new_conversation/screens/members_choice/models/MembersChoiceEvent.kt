package ru.vyapps.vkgram.new_conversation.screens.members_choice.models

import ru.vyapps.vkgram.new_conversation.UserModel

sealed class MembersChoiceEvent {

    object EnterScreen: MembersChoiceEvent()
    object ReloadScreen: MembersChoiceEvent()
    data class OnFriendClick(val user: UserModel): MembersChoiceEvent()
    data class OnItemListEnd(val itemCount: Int): MembersChoiceEvent()
}