package ru.gozerov.core

import android.content.Context
import ru.gozerov.ex_tech_task.di.AppComponent
import ru.gozerov.ex_tech_task.singleton.App

val Context.appComponent: AppComponent
    get() = when(this) {
        is App -> appComponent
        else -> this.applicationContext.appComponent
    }