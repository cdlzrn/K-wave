package com.cdlzrn.kwave.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "post")
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")                val id: Long = 0L,

    @ColumnInfo(name = "image")             val image: Int,
    @ColumnInfo(name = "like_count")        val likesCount: Int,
    @ColumnInfo(name = "reposts_count")     val repostsCount: Int,
    @ColumnInfo(name = "description")       val description: String,
    @ColumnInfo(name = "date_of_publication") val dateOfPublication: LocalDateTime,

    @ColumnInfo(name = "author_id")         val authorId: Long,
)

//data class PostWithAllData(
//    @Embedded val post: PostEntity,
//    @Relation(parentColumn = "author_id", entityColumn = "id") val author: UserEntity
//)
