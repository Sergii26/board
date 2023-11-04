package com.practice.board.ui.screens.add_ticket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.board.domain.model.Priority
import com.practice.board.domain.model.Task
import com.practice.board.domain.model.TaskState
import com.practice.board.domain.usecase.AddTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTicketViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase
): ViewModel() {
    val priorities = Priority.values().toList()

    fun onSubmit(title: String, description: String, priority: Priority) {
        viewModelScope.launch {
            addTaskUseCase(Task(title, description, priority = priority, TaskState.Open))
        }
    }
}