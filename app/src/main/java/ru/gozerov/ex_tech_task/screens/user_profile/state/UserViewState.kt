package ru.gozerov.ex_tech_task.screens.user_profile.state

import ru.gozerov.core.ViewState
import ru.gozerov.domain.user.models.UserApi

sealed class UserViewState: ViewState {

    object Loading: UserViewState()
    data class SuccessLoaded(val user: UserApi): UserViewState()
    data class Error(val error: String): UserViewState()

}
