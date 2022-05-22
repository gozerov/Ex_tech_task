package ru.gozerov.data.user.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.gozerov.data.user.models.DataUserApi

@Database(entities = [DataUserApi::class], version = 1)
abstract class UsersDatabase: RoomDatabase() {

    abstract fun getUserDao(): UsersDao

    companion object {

        const val name: String = "USERS_DB"

        private var database: UsersDatabase? = null

        @Synchronized
        fun getInstance(context: Context): UsersDatabase {
            return database ?: Room.databaseBuilder(context, UsersDatabase::class.java, name).build()
        }
    }

}