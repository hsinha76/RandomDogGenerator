package com.hsdroid.randomdoggenerator.data.repository

import com.google.gson.JsonObject
import com.hsdroid.randomdoggenerator.data.network.APIInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DogsRepository @Inject constructor(private val apiInterface: APIInterface) {

    fun getDogs(): Flow<JsonObject> = flow {
        emit(apiInterface.getData())
    }.flowOn(Dispatchers.IO)

}