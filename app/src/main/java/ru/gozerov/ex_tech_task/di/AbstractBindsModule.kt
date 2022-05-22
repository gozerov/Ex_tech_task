package ru.gozerov.ex_tech_task.di

import dagger.Binds
import dagger.Module
import ru.gozerov.data.user.repositories.UsersRepositoryImpl
import ru.gozerov.domain.user.repositories.UsersRepository
import javax.inject.Singleton

@Module
interface AbstractBindsModule {

    @Singleton
    @Binds
    @Suppress("unused")
    fun provideUsersRepository(usersRepositoryImpl: UsersRepositoryImpl): UsersRepository

}