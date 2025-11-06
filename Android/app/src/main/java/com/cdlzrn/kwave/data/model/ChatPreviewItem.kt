package com.cdlzrn.kwave.data.model

//TODO: Со временем разобраться
data class ChatPreviewItem(
    val id: Long,
//    val imageUrl: String,
    val image: Int,
    val title: String,
    val lastMessage: String,
    val time: String,
    val unreadCount: Int,
    val type: ChatType
)

enum class ChatType {
    DIALOG, GROUP
}