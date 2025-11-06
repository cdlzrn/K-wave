package com.cdlzrn.kwave.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cdlzrn.kwave.data.enums.CommentTarget
import java.time.LocalDateTime

@Entity(tableName = "comment")
data class CommentEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")                val id: Long = 0L,

    @ColumnInfo(name = "text")              val text: String,
    @ColumnInfo(name = "image")             val image: Int,
    @ColumnInfo(name = "date_of_publication") val dateOfPublication: LocalDateTime,

    @ColumnInfo(name = "author_id")         val authorId: Long,
    @ColumnInfo(name = "comment_target_id") val targetId: Long,
    @ColumnInfo(name = "comment_target_type") val targetType: CommentTarget,
)

//data class CommentWithAllData(
//    @Embedded val comment: CommentEntity,
//    @Relation(parentColumn = "author_id", entityColumn = "id") val author: UserEntity
//)