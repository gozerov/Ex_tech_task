package ru.gozerov.domain.user.usecases

import kotlinx.coroutines.flow.Flow
import ru.gozerov.core.UseCase
import ru.gozerov.domain.user.models.UserSimple
import ru.gozerov.domain.user.repositories.UsersRepository
import javax.inject.Inject

class GetUsers @Inject constructor (
    private val usersRepository: UsersRepository
): UseCase<Unit?, Flow<Map<String, List<UserSimple>>>> {

    override suspend fun execute(
        args: Unit?,
        onSuccess: (Flow<Map<String, List<UserSimple>>>) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            onSuccess(usersRepository.getUsers())
        } catch (e: Exception) {
            onError(e.localizedMessage)
        }
    }

}