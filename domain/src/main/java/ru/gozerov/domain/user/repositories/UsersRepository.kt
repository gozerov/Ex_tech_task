package ru.gozerov.domain.user.repositories

import kotlinx.coroutines.flow.Flow
import ru.gozerov.core.Repository
import ru.gozerov.domain.user.models.UserApi
import ru.gozerov.domain.user.models.UserSimple

interface UsersRepository: Repository {

    suspend fun getUsers(): Flow<Map<String, List<UserSimple>>>

    suspend fun getUserById(id: Int): UserApi

    suspend fun getUsersSingle(): Map<String, List<UserSimple>>

}