package ru.gozerov.ex_tech_task.screens.contacts_list.state

import kotlinx.coroutines.flow.Flow
import ru.gozerov.core.ViewState
import ru.gozerov.domain.user.models.UserSimple

sealed class ContactsViewState: ViewState {

    object Loading : ContactsViewState()
    data class SuccessLoaded(
        val data: Flow<Map<String, List<UserSimple>>>,
        val currentFilter: String?
        ) : ContactsViewState()

    data class Error(val error: String) : ContactsViewState()

}
