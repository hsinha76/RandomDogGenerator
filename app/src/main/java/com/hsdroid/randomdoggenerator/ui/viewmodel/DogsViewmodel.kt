package com.hsdroid.randomdoggenerator.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hsdroid.randomdoggenerator.data.repository.DogsRepository
import com.hsdroid.randomdoggenerator.utils.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogsViewmodel @Inject constructor(private val repository: DogsRepository) : ViewModel() {

    private val response: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.EMPTY)

    val _response: StateFlow<ApiState> = response

    fun getData() = viewModelScope.launch {

        repository.getDogs()
            .onStart {
                response.value = ApiState.LOADING
            }
            .catch {
                response.value = ApiState.FAILURE(it)
            }
            .collect {
                response.value = ApiState.SUCCESS(it.get("message").asString)
            }
    }
}