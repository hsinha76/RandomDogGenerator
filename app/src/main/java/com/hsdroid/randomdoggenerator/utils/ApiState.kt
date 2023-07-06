package com.hsdroid.randomdoggenerator.utils

sealed class ApiState {
    object LOADING : ApiState()
    class SUCCESS(val data: String) : ApiState()
    class FAILURE(val t: Throwable) : ApiState()
    object EMPTY : ApiState()
}
