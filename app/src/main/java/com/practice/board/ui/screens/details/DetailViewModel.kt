package com.practice.board.ui.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.practice.board.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val taskRepository: TaskRepository
): ViewModel() {
    init {
        val argument = savedStateHandle.get<Int>("taskId")
        Timber.d("task in view model: $argument")
    }
}