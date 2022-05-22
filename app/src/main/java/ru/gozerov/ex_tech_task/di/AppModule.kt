package ru.gozerov.ex_tech_task.di

import dagger.Module

@Module(includes = [AbstractBindsModule::class, RoomModule::class])
class AppModule