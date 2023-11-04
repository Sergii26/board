package com.practice.board.ui.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.practice.board.NavDest
import com.practice.board.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val taskRepository: TaskRepository
): ViewModel() {
    init {
        val taskId = savedStateHandle.get<Int>(NavDest.Detail.TASK_ID_KEY)
    }
}