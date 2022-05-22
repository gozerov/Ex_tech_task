package ru.gozerov.domain.user.usecases

import ru.gozerov.core.UseCase
import ru.gozerov.domain.user.models.UserApi
import ru.gozerov.domain.user.repositories.UsersRepository
import javax.inject.Inject

class GetUserById @Inject constructor(
    private val usersRepository: UsersRepository
): UseCase<Int, UserApi> {

    override suspend fun execute(
        args: Int,
        onSuccess: (UserApi) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            onSuccess(usersRepository.getUserById(args))
        } catch (e: Exception) {
            onError(e.localizedMessage)
        }
    }

}