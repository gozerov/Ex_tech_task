package ru.gozerov.data.user.providers

import android.content.Context
import javax.inject.Inject

class ResourceProvider @Inject constructor (
    private val context: Context
) {
    fun getString(resId: Int): String {
        return context.getString(resId)
    }
}