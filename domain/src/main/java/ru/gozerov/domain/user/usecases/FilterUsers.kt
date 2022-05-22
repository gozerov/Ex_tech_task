package ru.gozerov.domain.user.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.gozerov.core.UseCase
import ru.gozerov.domain.user.models.UserSimple
import ru.gozerov.domain.user.repositories.UsersRepository
import java.util.logging.Logger
import javax.inject.Inject

class FilterUsers @Inject constructor (
    private val usersRepository: UsersRepository
): UseCase<String?, Flow<Map<String, List<UserSimple>>>> {

    override suspend fun execute(
        args: String?,
        onSuccess: (Flow<Map<String, List<UserSimple>>>) -> Unit,
        onError: (String) -> Unit
    ) {
        try {

            val logger = Logger.getLogger(FilterUsers::class.java.name)
            logger.warning(args.toString())
            val users = usersRepository.getUsersSingle()
            if (args != null) {
                val newUsers = users.mapValues { entry ->
                    entry.value.filter { user ->
                        user.name.contains(args) or user.email.contains(args) or
                                user.name.equals(args, true) or (user.email.equals(args, true))
                    }
                }
                onSuccess(flowOf(newUsers))
            }
            else
                onSuccess(flowOf(users))

        } catch (e: Exception) {
            onError(e.localizedMessage)
        }

    }

}