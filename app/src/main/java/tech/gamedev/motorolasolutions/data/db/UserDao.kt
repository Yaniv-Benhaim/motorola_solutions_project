package tech.gamedev.motorolasolutions.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import tech.gamedev.motorolasolutions.data.models.User

@Dao
interface UserDao {

    @Update
    suspend fun updateUser(user: User)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users")
    fun getUsers() : LiveData<List<User>>

    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()
}