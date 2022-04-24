package me.vislavy.vkgram.api.local.entities

import androidx.room.*
import com.vk.sdk.api.base.dto.BaseSex
import com.vk.sdk.api.friends.dto.FriendsFriendStatusStatus
import com.vk.sdk.api.users.dto.UsersUserFull
import java.util.*

@Entity(tableName = "stored_users")
@TypeConverters(FriendStatusConverter::class, DateConverter::class, BaseSexConverter::class)
data class StoredUser(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "photo_url") val photoUrl: String,
    val name: String,
    val nameGen: String,
    @ColumnInfo(name = "screen_name") val screenName: String,
    val verified: Boolean,
    val isYourProfile: Boolean,
    @ColumnInfo(name = "last_seen") val lastSeen: Date,
    val status: String?,
    val sex: BaseSex,
    val country: String?,
    val city: String?,
    val birthday: String?,
    val friendCount: Int,
    val mutualFriendCount: Int,
    val followerCount: Int,
    @ColumnInfo(name = "can_write_private_message") val canWritePrivateMessage: Boolean,
    @ColumnInfo(name = "friend_status") val friendStatus: FriendsFriendStatusStatus,
)

@Suppress("Unused")
class FriendStatusConverter {

    @TypeConverter
    fun toFriendStatus(value: String): FriendsFriendStatusStatus = enumValueOf(value)

    @TypeConverter
    fun fromFriendStatus(value: FriendsFriendStatusStatus): Int = value.ordinal
}

@Suppress("Unused")
class DateConverter {

    @TypeConverter
    fun toDate(value: Long): Date = Date(value)

    @TypeConverter
    fun fromDate(value: Date): Long = value.time
}

@Suppress("Unused")
class BaseSexConverter {

    @TypeConverter
    fun toBaseSex(value: String): BaseSex = enumValueOf(value)

    @TypeConverter
    fun fromBaseSex(value: BaseSex): Int = value.ordinal
}

fun UsersUserFull.convertToStoredUser(): StoredUser =
    StoredUser(
        id = id.value,
        photoUrl = photoMax!!,
        name = "$firstName $lastName",
        nameGen = "$firstNameGen $lastNameGen",
        screenName = domain!!,
        verified = verified!!.value == 1,
        isYourProfile = false,
        lastSeen = Date(lastSeen!!.time!! * 1000L),
        status = status,
        sex = sex!!,
        country = country?.title,
        city = city?.title,
        birthday = bdate,
        friendCount = counters?.friends ?: 0,
        mutualFriendCount = counters?.mutualFriends ?: 0,
        followerCount = counters?.followers ?: 0,
        friendStatus = friendStatus!!,
        canWritePrivateMessage = canWritePrivateMessage!!.value == 1
    )