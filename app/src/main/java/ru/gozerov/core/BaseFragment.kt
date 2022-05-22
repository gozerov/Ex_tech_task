package ru.gozerov.core

import androidx.fragment.app.Fragment

abstract class BaseFragment<T: ViewState>: Fragment() {

    abstract fun renderState(state: T)

}