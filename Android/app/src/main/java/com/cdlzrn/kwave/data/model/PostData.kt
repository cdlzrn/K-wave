package com.cdlzrn.kwave.data.model

import java.time.LocalDateTime

data class PostData (
    val description: String,
    val image: Int,
    val time: LocalDateTime,
    val countLike: Int = 0,
    val countComment: Int = 0,
    val countRepost: Int = 0,
    val authorName: String,
    val authorImage: Int,
)