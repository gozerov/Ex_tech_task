package ru.gozerov.ex_tech_task.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.gozerov.data.user.cache.UsersDatabase

@Module
class RoomModule {

    @Provides
    fun provideUsersDao(context: Context) =
        UsersDatabase.getInstance(context).getUserDao()

}