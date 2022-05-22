package ru.gozerov.data.user.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.toLocalDate
import ru.gozerov.data.R
import ru.gozerov.data.user.cache.UsersDao
import ru.gozerov.data.user.models.DataUserApi
import ru.gozerov.data.user.models.DataUserSimple
import ru.gozerov.data.user.providers.ResourceProvider
import ru.gozerov.domain.user.models.UserApi
import ru.gozerov.domain.user.models.UserSimple
import ru.gozerov.domain.user.repositories.UsersRepository
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor (
    private val usersDao: UsersDao,
    private val resourceProvider: ResourceProvider
        ): UsersRepository {

    override suspend fun getUsers(): Flow<Map<String, List<UserSimple>>> = withContext(Dispatchers.IO) {
        if (usersDao.getUsersCount()==0) {
            val users = generateUsers()
            usersDao.firstInitialization(users)
            return@withContext flowOf(sortUsers(users))
        } else {
            return@withContext usersDao.getUsers().map { sortUsers(it) }
        }
    }

    override suspend fun getUserById(id: Int): UserApi {
        return usersDao.getUserById(id).toUserApi()
    }

    override suspend fun getUsersSingle(): Map<String, List<UserSimple>> = withContext(Dispatchers.IO) {
        return@withContext sortUsers(usersDao.getUsersSingle())
    }

    private fun generateUsers(): List<DataUserApi> {
        return listOf<DataUserApi>(
            DataUserApi(0, null, "User3", "Designer", "1990-01-25", "19", "+79091234569", "user@mail.ru"),
            DataUserApi(1, null, "User2", "Analyst", "1990-05-19", "19", "+79091234569", "user@mail.ru"),
            DataUserApi(2, null, "User4", "Designer", "1990-02-15", "19", "+79091234569", "user@mail.ru"),
            DataUserApi(3, null, "User5", "Manager", "1990-03-28", "19", "+79091234569", "user@mail.ru"),
            DataUserApi(4, null, "User1", "IOS", "1990-01-07", "19", "+79091234569", "user@mail.ru"),
            DataUserApi(5, null, "User6", "Android", "1990-06-17", "19", "+79091234569", "user@mail.ru"),
            DataUserApi(7, null, "User1", "Android", "1990-04-18", "19", "+79091234569", "user@mail.ru"),
            DataUserApi(6, null, "User5", "Analyst", "1990-09-13", "19", "+79091234569", "user@mail.ru"),
            DataUserApi(8, null, "User9", "Manager", "1990-02-22", "19", "+79091234569", "user@mail.ru"),
        )
    }

    private fun sortUsers(users: List<DataUserApi>): Map<String, List<UserSimple>> {
        val sortedUser = mutableMapOf<String, List<UserSimple>>()
        val designers = users.filter { it.profession == resourceProvider.getString(R.string.designer) }
        val analysts = users.filter { it.profession == resourceProvider.getString(R.string.analyst) }
        val managers = users.filter { it.profession == resourceProvider.getString(R.string.manager) }
        val ios = users.filter { it.profession == resourceProvider.getString(R.string.ios) }
        val android = users.filter { it.profession == resourceProvider.getString(R.string.android) }
        sortedUser[resourceProvider.getString(R.string.all)] = users.toListUserSimple()
        sortedUser[resourceProvider.getString(R.string.designers)] = designers.toListUserSimple()
        sortedUser[resourceProvider.getString(R.string.analysts)] = analysts.toListUserSimple()
        sortedUser[resourceProvider.getString(R.string.managers)] = managers.toListUserSimple()
        sortedUser[resourceProvider.getString(R.string.ios)] = ios.toListUserSimple()
        sortedUser[resourceProvider.getString(R.string.android)] = android.toListUserSimple()
        return sortedUser
        }
    }

    // Converter-functions

    private fun List<DataUserApi>.toListUserSimple(): List<UserSimple> {
        return this.toListDataUserSimple().map { it.toUserSimple()}
    }

    private fun DataUserApi.toUserApi(): UserApi {
        return UserApi(
            id = this.id,
            image = this.imageUrl,
            name = this.name,
            profession = this.profession,
            birthday = this.birthday.toLocalDate(),
            age = this.age,
            phone = this.phone,
            email = this.email
        )
    }

    private fun DataUserApi.toDataUserSimple(): DataUserSimple {
       return DataUserSimple(
           id = this.id,
           imageUrl = this.imageUrl,
           name = this.name,
           profession = this.profession,
           birthday = this.birthday,
           email = this.email
       )
    }

    private fun List<DataUserApi>.toListDataUserSimple(): List<DataUserSimple> {
        return this.map { it.toDataUserSimple()}
    }

    private fun DataUserSimple.toUserSimple(): UserSimple {
        return UserSimple(
            id = this.id,
            image = this.imageUrl,
            name = this.name,
            profession = this.profession,
            birthday = this.birthday.toLocalDate(),
            email = this.email
        )
    }