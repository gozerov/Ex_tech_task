package ru.gozerov.core

interface UseCase<T,R> {
    suspend fun execute(
        args: T,
        onSuccess: (R) -> Unit,
        onError: (String) -> Unit
    )
}