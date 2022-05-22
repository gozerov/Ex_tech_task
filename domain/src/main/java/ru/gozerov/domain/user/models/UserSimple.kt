package ru.gozerov.domain.user.models

import kotlinx.datetime.LocalDate

data class UserSimple(
    val id: Int,
    val image: String?,
    val name: String,
    val profession: String,
    val birthday: LocalDate,
    val email: String
)
