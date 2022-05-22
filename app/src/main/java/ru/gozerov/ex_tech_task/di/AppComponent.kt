package ru.gozerov.ex_tech_task.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.gozerov.ex_tech_task.screens.contacts_list.ContactsListFragment
import ru.gozerov.ex_tech_task.screens.user_profile.UserProfileFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(contactsListFragment: ContactsListFragment)
    fun inject(contactsListFragment: UserProfileFragment)

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun context(context: Context): Builder

    }

}