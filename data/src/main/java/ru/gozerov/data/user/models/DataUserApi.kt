package ru.gozerov.data.user.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.gozerov.data.user.cache.UsersDatabase
import kotlinx.datetime.LocalDate

@Entity(tableName = UsersDatabase.name)
data class DataUserApi(
    @PrimaryKey
    val id: Int,
    val imageUrl: String?,
    val name: String,
    val profession: String,
    val birthday: String,
    val age: String,
    val phone: String,
    val email: String
)
