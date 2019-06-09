package com.nikitamaslov.postdomain.model

sealed class Optional<out T : Any>(val value: T?)

class Some<out T : Any>(value: T) : Optional<T>(value)

object None : Optional<Nothing>(null)