package ru.gozerov.ex_tech_task.screens.user_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.gozerov.domain.user.usecases.GetUserById
import ru.gozerov.ex_tech_task.screens.user_profile.state.UserViewState
import ru.gozerov.ex_tech_task.screens.user_profile.state.UserViewState.Loading
import javax.inject.Inject

class UserProfileViewModel(
    private val getUserById: GetUserById
): ViewModel() {

    private val _viewState = MutableStateFlow<UserViewState>(Loading)
    val viewState: StateFlow<UserViewState> = _viewState.asStateFlow()

    fun loadUser(id: Int) = viewModelScope.launch {
        getUserById.execute(
            id,
            onSuccess = { user ->
                _viewState.tryEmit(UserViewState.SuccessLoaded(user))
            },
            onError = { e ->
                _viewState.tryEmit(UserViewState.Error(e))
            })
    }

    class Factory @Inject constructor (
        private val getUserById: GetUserById
    ): ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return UserProfileViewModel(getUserById) as T
        }

    }

}