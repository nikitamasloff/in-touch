package com.nikitamaslov.core.lifecycle.model

import androidx.annotation.StringRes

data class StringResEvent(@StringRes private val resId: Int) {

    var hasBeenHandled: Boolean = false
        private set

    fun getResIdIfNotHandled(): Int? = resId
        .takeIf { !hasBeenHandled }
        ?.also { hasBeenHandled = true }

    fun peekResId(): Int = resId
}