package com.cdlzrn.kwave.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.cdlzrn.kwave.R
import java.time.LocalDate

@Entity(
    tableName = "user",
    indices = [
        Index(value = ["mail"], unique = true),
        Index(value = ["phone"], unique = true),
        Index(value = ["nick_name"], unique = true)
    ]
)
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")            val id: Long = 0L,

    @ColumnInfo(name = "name")          val name: String,
    @ColumnInfo(name = "avatar")        val avatar: Int = R.drawable.new_user,
    @ColumnInfo(name = "mail")          val mail: String?,
    @ColumnInfo(name = "phone")         val phone: String,
    @ColumnInfo(name = "rate")          val rate: Float = 0f,
    @ColumnInfo(name = "nick_name")     val nickName: String?,
    @ColumnInfo(name = "password_hash") val passwordHash: String,
    @ColumnInfo(name = "date_of_birth") val dateOfBirth: LocalDate,
    @ColumnInfo(name = "is_artist")     val isArtist: Boolean = false,
)

//data class UserWithAllData(
//    @Embedded val user: UserEntity,
//    @Relation(parentColumn = "id", entityColumn = "author_id") val posts: List<PostEntity>,
//    @Relation(parentColumn = "id", entityColumn = "seller_id") val products: List<ProductEntity>,
//    @Relation(
//        entity = UserEntity::class,
//        parentColumn = "id",
//        entityColumn = "id",
//        associateBy = Junction(
//            value = UserFollowEntity::class,
//            parentColumn = "followerId",
//            entityColumn = "followedId"
//        )
//    )
//    val followed: List<UserEntity>,
//)