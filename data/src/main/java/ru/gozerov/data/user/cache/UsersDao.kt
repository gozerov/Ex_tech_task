package ru.gozerov.data.user.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.gozerov.data.user.models.DataUserApi

@Dao
interface UsersDao {

    //Suspend только тогда, когда возвращаем асинхронную штуку

    @Query("SELECT * FROM users_db")
    fun getUsers() : Flow<List<DataUserApi>>

    @Query("SELECT * FROM users_db")
    fun getUsersSingle() : List<DataUserApi>

    @Query("SELECT * FROM users_db WHERE id = :id")
    suspend fun getUserById(id: Int) : DataUserApi

    @Insert
    suspend fun firstInitialization(users: List<DataUserApi>)

    @Query("SELECT count(*) FROM users_db")
    suspend fun getUsersCount(): Int
}