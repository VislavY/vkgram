package me.vislavy.vkgram.profile.models

import com.vk.sdk.api.messages.dto.MessagesHistoryAttachment
import me.vislavy.vkgram.api.local.entities.StoredUser
import me.vislavy.vkgram.core.base.DisplayState

data class ProfileState(
    val displayState: DisplayState = DisplayState.Loading,
    val user: StoredUser? = null,
    val isYourProfile: Boolean = false,
    val photoAttachments: List<MessagesHistoryAttachment> = emptyList(),
    val photoAttachmentListEnd: Boolean = false,
    val videoAttachments: List<MessagesHistoryAttachment> = emptyList(),
    val videoAttachmentListEnd: Boolean = false,
    val audioAttachments: List<MessagesHistoryAttachment> = emptyList(),
    val audioAttachmentListEnt: Boolean = false,
    val fileAttachments: List<MessagesHistoryAttachment> = emptyList(),
    val fileAttachmentListEnd: Boolean = false
)
