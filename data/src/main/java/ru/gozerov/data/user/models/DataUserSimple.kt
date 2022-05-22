package ru.gozerov.data.user.models

data class DataUserSimple(
    val id: Int,
    val imageUrl: String?,
    val name: String,
    val profession: String,
    val birthday: String,
    val email: String
)
