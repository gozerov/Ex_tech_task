package ru.gozerov.ex_tech_task.singleton

import android.app.Application
import ru.gozerov.ex_tech_task.di.AppComponent
import ru.gozerov.ex_tech_task.di.DaggerAppComponent

class App: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        appComponent = DaggerAppComponent
            .builder()
            .context(this)
            .build()
        super.onCreate()
    }

}