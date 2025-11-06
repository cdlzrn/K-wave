package com.cdlzrn.kwave.data.converter

import androidx.room.TypeConverter
import com.cdlzrn.kwave.data.enums.CommentTarget

open class CommentTargetConverter {
    @TypeConverter
    fun fromCommentTarget(commentTarget: CommentTarget?): String?{
        return commentTarget?.name
    }

    @TypeConverter
    fun toCommentTarget(value: String?): CommentTarget?{
        return value?.let { CommentTarget.valueOf(it) }
    }
}