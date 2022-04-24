package me.vislavy.vkgram.core.repositories

import com.vk.api.sdk.VK.executeSync
import com.vk.sdk.api.messages.MessagesService
import com.vk.sdk.api.messages.dto.MessagesGetHistoryAttachmentsMediaType
import com.vk.sdk.api.messages.dto.MessagesHistoryAttachment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DialogRepository @Inject constructor() {

    suspend fun fetchDialogAttachments(
        dialogId: Long,
        type: MessagesGetHistoryAttachmentsMediaType,
        count: Int = 20,
        offset: Int = 0

    ): List<MessagesHistoryAttachment> = withContext(Dispatchers.IO) {
        val request = MessagesService().messagesGetHistoryAttachments(
            peerId = dialogId.toInt(),
            mediaType = type,
            startFrom = offset.toString(),
            count = count
        )
        executeSync(request).items ?: emptyList()
    }
}