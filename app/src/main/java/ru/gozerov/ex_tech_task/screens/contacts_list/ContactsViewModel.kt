package ru.gozerov.ex_tech_task.screens.contacts_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.gozerov.domain.user.usecases.FilterUsers
import ru.gozerov.domain.user.usecases.GetUsers
import ru.gozerov.domain.user.usecases.SortUsers
import ru.gozerov.ex_tech_task.screens.contacts_list.state.ContactsViewState
import javax.inject.Inject

class ContactsViewModel(
    private val getUsers: GetUsers,
    private val sortUsers: SortUsers,
    private val filterUsers: FilterUsers
): ViewModel() {

    private val _viewState = MutableStateFlow<ContactsViewState>(ContactsViewState.Loading)
    val viewState: StateFlow<ContactsViewState> = _viewState.asStateFlow()

    private var successState: ContactsViewState.SuccessLoaded? = null

    init {
        viewModelScope.launch {
            getUsers.execute(null,
                onSuccess = { data ->
                    successState = ContactsViewState.SuccessLoaded(data, null)
                    successState?.let { s ->
                        _viewState.tryEmit(s)
                    }

                },
                onError = { error ->
                    Log.e("TAG", error)
                    _viewState.tryEmit(ContactsViewState.Error(error))
                }
            )
        }
    }

    fun sortUsers(filter: String?) {
        viewModelScope.launch {
            sortUsers.execute(
                filter,
                onSuccess = { users ->
                    successState = ContactsViewState.SuccessLoaded(users, filter)
                    successState?.let { s ->
                        _viewState.tryEmit(s)
                    }
                },
                onError = { e ->
                    _viewState.tryEmit(ContactsViewState.Error(e))

                }
            )
        }
    }

    fun filterUsers(newText: String?) {
        viewModelScope.launch {
            filterUsers.execute(
                newText,
                onSuccess = { users ->
                    successState = ContactsViewState.SuccessLoaded(users, successState?.currentFilter)
                    successState?.let { s ->
                        _viewState.tryEmit(s)
                    }
                },
                onError = { e ->
                    _viewState.tryEmit(ContactsViewState.Error(e))
                }
            )
        }
    }

    class Factory @Inject constructor(
        private val getUsers: GetUsers,
        private val sortUsers: SortUsers,
        private val filterUsers: FilterUsers
    ): ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ContactsViewModel(getUsers, sortUsers, filterUsers) as T
        }
    }

}