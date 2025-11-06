package com.cdlzrn.kwave.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.cdlzrn.kwave.data.entity.UserEntity
import com.cdlzrn.kwave.data.model.UserData
import com.cdlzrn.kwave.data.model.UserRegistrationData
import org.mindrot.jbcrypt.BCrypt
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun toUserEntity(user: UserRegistrationData): UserEntity{

    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val date =  LocalDate.parse(user.dateOfBirth, formatter)

    return UserEntity(
        name = user.name,
        mail = user.mail.ifBlank { null },
        phone = user.phone,
        dateOfBirth = date,
        nickName = null,
        passwordHash = BCrypt.hashpw(
            user.password,
            BCrypt.gensalt(12)
        )
    )
}

fun toUserData(user: UserEntity): UserData{
    return UserData(
        id = user.id,
        name = user.name,
        avatar = user.avatar,
        mail = user.mail,
        phone = user.phone,
        nickName = user.nickName,
        rate = user.rate,
        dateOfBirth = user.dateOfBirth,
    )
}

//fun toUserData(user: UserWithAllData): UserData{
//    return UserData(
//        id = user.user.id,
//        name = user.user.name,
//        avatar = user.user.avatar,
//        mail = user.user.mail,
//        phone = user.user.phone,
//        nickName = user.user.nickName,
//        rate = user.user.rate,
//        dateOfBirth = user.user.dateOfBirth,
//        passwordHash = user.user.passwordHash,
//        followed = user.followed.map {entity -> toUserData(entity)}
//    )
//
//}