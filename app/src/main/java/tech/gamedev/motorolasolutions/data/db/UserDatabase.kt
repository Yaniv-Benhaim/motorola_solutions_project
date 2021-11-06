package tech.gamedev.motorolasolutions.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import tech.gamedev.motorolasolutions.data.models.User
import tech.gamedev.motorolasolutions.data.other.Constants.DB_NAME

@Database(entities = [User::class], version = 2)
abstract class UserDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

    companion object {

        fun create(context: Context): UserDatabase {
            return Room.databaseBuilder(
                context,
                UserDatabase::class.java,
                DB_NAME
            ).fallbackToDestructiveMigration()
             .build()
        }
    }
}