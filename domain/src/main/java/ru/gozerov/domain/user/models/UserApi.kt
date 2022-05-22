package ru.gozerov.domain.user.models

import kotlinx.datetime.LocalDate

data class UserApi(
    val id: Int,
    val image: String?,
    val name: String,
    val profession: String,
    val birthday: LocalDate,
    val age: String,
    val phone: String,
    val email: String
)

