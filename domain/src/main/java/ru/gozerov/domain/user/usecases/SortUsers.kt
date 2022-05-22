package ru.gozerov.domain.user.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.gozerov.core.UseCase
import ru.gozerov.domain.user.models.UserSimple
import ru.gozerov.domain.user.repositories.UsersRepository
import javax.inject.Inject

class SortUsers @Inject constructor (
    private val usersRepository: UsersRepository
): UseCase<String?, Flow<Map<String, List<UserSimple>>>> {

    override suspend fun execute(
        args: String?,
        onSuccess: (Flow<Map<String, List<UserSimple>>>) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val users = usersRepository.getUsersSingle()
            val newUsers = when (args) {
                FILTER1 -> {
                    users.mapValues { entry ->
                        entry.value.sortedBy { user -> user.name }
                    }
                }
                FILTER2 ->  {
                    users.mapValues { entry ->
                        entry.value.sortedByDescending { user -> user.birthday }.reversed()
                    }
                }
                else -> users
            }
            onSuccess(flowOf(newUsers))

        } catch (e: Exception) {
            onError(e.localizedMessage)
        }
    }

    companion object Filters {
        const val FILTER1 = "По алфавиту"
        const val FILTER2 = "По дню рождения"
    }
}