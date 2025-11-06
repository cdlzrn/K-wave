package com.cdlzrn.kwave.data.database

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cdlzrn.kwave.R
import com.cdlzrn.kwave.data.converter.CommentTargetConverter
import com.cdlzrn.kwave.data.converter.CurrencyConverter
import com.cdlzrn.kwave.data.converter.LocalDateConverter
import com.cdlzrn.kwave.data.converter.LocalDateTimeConverter
import com.cdlzrn.kwave.data.dao.CartDao
import com.cdlzrn.kwave.data.dao.PostDao
import com.cdlzrn.kwave.data.dao.ProductDao
import com.cdlzrn.kwave.data.dao.UserDao
import com.cdlzrn.kwave.data.dao.UserFollowDao
import com.cdlzrn.kwave.data.entity.CartEntity
import com.cdlzrn.kwave.data.entity.CommentEntity
import com.cdlzrn.kwave.data.entity.PostEntity
import com.cdlzrn.kwave.data.entity.ProductEntity
import com.cdlzrn.kwave.data.entity.UserEntity
import com.cdlzrn.kwave.data.entity.UserFollowEntity
import com.cdlzrn.kwave.data.enums.Currency
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt
import java.time.LocalDate
import java.time.LocalDateTime

@TypeConverters( value = [
    LocalDateTimeConverter::class,
    LocalDateConverter::class,
    CommentTargetConverter::class,
    CurrencyConverter::class] )
@Database(
    entities = [
        UserEntity::class,
        PostEntity::class,
        CartEntity::class,
        ProductEntity::class,
        CommentEntity::class,
        UserFollowEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun postDao(): PostDao
    abstract fun cartDao(): CartDao
    abstract fun productDao(): ProductDao
    abstract fun followDao(): UserFollowDao

    companion object{
        @Volatile
        var INSTANCE: AppDataBase? = null
        fun getDataBase(context: Context, scope: CoroutineScope): AppDataBase{

            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context = context,
                    klass = AppDataBase::class.java,
                    name = "kwave_db"
                )
                .fallbackToDestructiveMigration(true)
                .addCallback(DatabaseCallback(scope))
                .build()

                INSTANCE = instance
                return instance
            }
        }
    }

    private class DatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {

        @RequiresApi(Build.VERSION_CODES.O)
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            scope.launch(Dispatchers.IO) {
                INSTANCE?.let { database ->
                    populateDatabase(
                        database.userDao(),
                        database.postDao(),
                        database.productDao()
                    )
                } ?: Log.e("KwaveDB", "Fatal: AppDataBase INSTANCE was null after creation.")
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
suspend fun populateDatabase(
    userDao: UserDao,
    postDao: PostDao,
    productDao: ProductDao
) {
    val usersIds: List<Long> = fillUsers(userDao)
    fillProducts(productDao, usersIds)
    fillPosts(postDao, usersIds)
}

@RequiresApi(Build.VERSION_CODES.O)
private suspend fun fillUsers(userDao: UserDao): List<Long> {
    val names = listOf(
        "The Boyz", "aespa", "DAY6", "TREASURE", "Kep1er", "Jay Park", "P1Harmony",
        "IVE", "Super Junior", "NCT DREAM", "RIIZE", "WINNER", "SEVENTEEN", "TAN",
        "New Jeans", "EXO", "PSY", "&TEAM", "KARD", "ENHYPEN", "Jessi", "Shine",
        "BTS", "The Rose", "Ateez"
    )
    val avatars = listOf(
        R.drawable.interests_0, R.drawable.interests_1, R.drawable.interests_2,
        R.drawable.interests_3, R.drawable.interests_4, R.drawable.interests_5,
        R.drawable.interests_6, R.drawable.interests_7, R.drawable.interests_8,
        R.drawable.interests_9, R.drawable.interests_10, R.drawable.interests_11,
        R.drawable.interests_12, R.drawable.interests_13, R.drawable.interests_14,
        R.drawable.interests_15, R.drawable.interests_16, R.drawable.interests_17,
        R.drawable.interests_18, R.drawable.interests_19, R.drawable.interests_20,
        R.drawable.interests_21, R.drawable.bts, R.drawable.the_rose, R.drawable.ateez
    )
    val mails = listOf(
        "The_Boyz@gmail.com",
        "aespa@gmail.com",
        "DAY6@gmail.com",
        "TREASURE@gmail.com",
        "Kep1er@gmail.com",
        "Jay_Park@gmail.com",
        "P1Harmony@gmail.com",
        "IVE@gmail.com",
        "Super_Junior@gmail.com",
        "NCT_DREAM@gmail.com",
        "RIIZE@gmail.com",
        "WINNER@gmail.com",
        "SEVENTEEN@gmail.com",
        "TAN@gmail.com",
        "New_Jeans@gmail.com",
        "EXO@gmail.com",
        "PSY@gmail.com",
        "&TEAM@gmail.com",
        "KARD@gmail.com",
        "ENHYPEN@gmail.com",
        "Jessi@gmail.com",
        "Shine@gmail.com",
        "BTS@gmail.com",
        "TheRose@gmail.com",
        "Ateez@gmail.com",
    )
    val phones = listOf(
        "+70000000000", "+70000000001", "+70000000002", "+70000000003", "+70000000004",
        "+70000000005", "+70000000006", "+70000000007", "+70000000008", "+70000000009",
        "+70000000010", "+70000000011", "+70000000012", "+70000000013", "+70000000014",
        "+70000000015", "+70000000016", "+70000000017", "+70000000018", "+70000000019",
        "+70000000020", "+70000000021", "+70000000022", "+70000000023", "+70000000024"
    )
    val rates = listOf(
        5.0f, 4.9f, 4.8f, 5.0f, 4.9f, 4.8f, 5.0f, 4.9f, 4.8f, 5.0f,
        5.0f, 4.9f, 4.8f, 5.0f, 4.9f, 4.8f, 5.0f, 4.9f, 4.8f, 5.0f,
        5.0f, 4.9f, 4.8f, 5.0f, 4.9f
    )
    val nickNames = names
    val dateOfBirth = listOf(
        LocalDate.parse("2017-12-06"), LocalDate.parse("2020-11-17"), LocalDate.parse("2015-09-07"),
        LocalDate.parse("2020-08-07"), LocalDate.parse("2022-01-03"), LocalDate.parse("1987-04-25"),
        LocalDate.parse("2020-10-28"), LocalDate.parse("2021-12-01"), LocalDate.parse("2005-11-06"),
        LocalDate.parse("2016-08-25"), LocalDate.parse("2023-09-04"), LocalDate.parse("2014-08-17"),
        LocalDate.parse("2015-05-26"), LocalDate.parse("2022-03-10"), LocalDate.parse("2022-07-22"),
        LocalDate.parse("2014-08-05"), LocalDate.parse("2001-05-10"), LocalDate.parse("2022-12-07"),
        LocalDate.parse("2017-07-19"), LocalDate.parse("2020-11-30"), LocalDate.parse("1988-12-17"),
        LocalDate.parse("2008-05-25"), LocalDate.parse("2013-06-13"), LocalDate.parse("2017-08-03"),
        LocalDate.parse("2018-10-24"),
    )
    val initialUsers: List<UserEntity> = buildList {
        for (i in 0..names.size - 1) {
            add(
                UserEntity(
                    name = names[i],
                    avatar = avatars[i],
                    mail = mails[i],
                    phone = phones[i],
                    rate = rates[i],
                    nickName = nickNames[i],
                    passwordHash = BCrypt.hashpw(names[i].reversed(), BCrypt.gensalt(12)),
                    dateOfBirth = dateOfBirth[i], isArtist = true
                )
            )
        }
    }

    return userDao.insertAll(initialUsers)
}

@RequiresApi(Build.VERSION_CODES.O)
private suspend fun fillProducts(
    productDao: ProductDao,
    userIds: List<Long>
) {

    val name = listOf(
        "Портрет Тэхёна BTS", "Карты BTS Fake love", "Брелок Тэхёе BTS", "BTS PTD карты", "Орешки биг боб",
        "The Rose медиатор", "The Rose футболка", "The Rose лайтстик", "The Rose Heal альбом", "Портрет The Rose",
        "Ateez брелок", "Ateez открытка", "Ateez шоппер", "Ateez брелок", "Ateez карта Ёсан"
    )

    val image = listOf(
        R.drawable.merch_0, R.drawable.merch_5, R.drawable.merch_4, R.drawable.merch_6, R.drawable.merch_7,
        R.drawable.merch_8, R.drawable.merch_9, R.drawable.merch_10, R.drawable.merch_11, R.drawable.merch_12,
        R.drawable.merch_13, R.drawable.merch_14, R.drawable.merch_15, R.drawable.merch_16, R.drawable.merch_17,
    )

    val price = listOf(
        1000, 800, 1250, 1000, 1000,
        800, 1000, 6000, 1900, 1000,
        1000, 700, 1550, 1000, 1000
    )

    val desc = listOf(
        "Портрет Тэхёна BTS маслом. Размер 25х30", "Карты BTS Fake love", "Брелок Тэхёе BTS", "BTS PTD карты", "Орешки биг боб",
        "The Rose медиатор", "The Rose футболка", "The Rose лайтстик", "The Rose Heal альбом", "Портрет The Rose",
        "Ateez брелок", "Ateez открытка", "Ateez шоппер", "Ateez брелок", "Ateez карта Ёсан"
    )

    val deliverPrice = listOf(
        500, 500, 500, 600, 500,
        500, 500, 500, 600, 500,
        500, 500, 500, 600, 500,
    )

    val dayBeforeDelivery = listOf(
        2, 1, 2, 3, 2,
        2, 1, 2, 3, 2,
        2, 1, 2, 3, 2,
    )

    val sellerId = listOf(
        userIds[22], userIds[22],userIds[22], userIds[22], userIds[22],
        userIds[23], userIds[23],userIds[23], userIds[23], userIds[23],
        userIds[24], userIds[24],userIds[24], userIds[24], userIds[24],
    )

    val initialProducts: List<ProductEntity> = buildList {
        for (i in 0..image.size - 1) {
            add(
                ProductEntity(
                    name = name[i],
                    image = image[i],
                    price = price[i],
                    currency = Currency.RUB,
                    description = desc[i],
                    deliveryPrice = deliverPrice[i],
                    dayBeforeDelivery = dayBeforeDelivery[i],
                    sellerId = sellerId[i]
                )
            )
        }
    }

    productDao.insertAll(initialProducts)
}

@RequiresApi(Build.VERSION_CODES.O)
private suspend fun fillPosts(
    postDao: PostDao,
    userIds: List<Long>
) {
    val image = listOf(
        R.drawable.post_0,
        R.drawable.post_1,
    )

    val likes = listOf(
        12, 12
    )

    val reposts = listOf(
        12, 12
    )

    val desc = listOf(
        "Обновление Instagram Bazaar Korea:\n\nДжин в интервью -\n\nОн рисует букву \"V\" своими длинными пальцами и улыбается, как мальчик, но когда съемки начинаются всерьез, он возвращается к лицу красивого взрослого мужчины, как будто этого никогда раньше не случалось. Между игривостью и серьезностью. Когда-то я думал, что истинное сердце Джина находится где-то между этими двумя. Эта мысль была ошибочной. Оба были Джинами.\n\nГлавный герой сентябрьского номера Bazaar, наполненное душой интервью и фото Джина находятся в сентябрьском номере.",
        "Are You Sure?! уже доступен с английскими и русскими авто субтитрами. Ссылки для скачивания добавлены в раздел для донатов.\n" +
                "\n" +
                "Следующая серия выйдет 15 августа после 10:00 МСК\n" +
                "\n" +
                "Всем приятного просмотра."
    )

    val date = listOf(
        LocalDateTime.of(2024, 9, 10, 15, 0),
        LocalDateTime.of(2024, 8, 3, 12, 0),
    )

    val author = listOf(
        userIds[22], userIds[22]
    )

    val initialPosts: List<PostEntity> = buildList {
        for (i in 0..image.size - 1) {
            add(
                PostEntity(
                    image = image[i],
                    likesCount = likes[i],
                    repostsCount = reposts[i],
                    description = desc[i],
                    dateOfPublication = date[i],
                    authorId = author[i]
                )
            )
        }
    }

    postDao.insertAll(initialPosts)
}

