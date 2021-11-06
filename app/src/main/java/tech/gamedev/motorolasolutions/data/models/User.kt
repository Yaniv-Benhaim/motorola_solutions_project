package tech.gamedev.motorolasolutions.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.ReadableDateTime
import tech.gamedev.motorolasolutions.data.other.Constants.USER_TABLE
import java.text.SimpleDateFormat
import java.time.Year
import java.util.*

@Entity(tableName = USER_TABLE)
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = true) val room_id: Int = 0,
    val fullName: String,
    val profileImage: String,
    val dateOfBirth: String,
    val email: String
) : Parcelable {

    //This function gets the local date and returns the difference between today and upcoming birthday
    fun getDaysTillBirthday(): Days {

        var nextBirthday = DateTime("${DateTime.now().year}${dateOfBirth.substring(4)}")
        return if(nextBirthday.isAfterNow) {
            Days.daysBetween(DateTime.now(), nextBirthday)
        } else {
            nextBirthday = DateTime("${DateTime.now().year+1}${dateOfBirth.substring(4)}")
            Days.daysBetween(DateTime.now(), nextBirthday)
        }
    }
}