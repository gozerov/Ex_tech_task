package ru.gozerov.core

interface ActionListener<T> {
    fun onClick(args: T)
}