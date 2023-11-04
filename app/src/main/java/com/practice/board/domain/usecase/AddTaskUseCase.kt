package com.practice.board.domain.usecase

import com.practice.board.domain.model.Task
import com.practice.board.domain.repository.TaskRepository

class AddTaskUseCase(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(task: Task) {
        taskRepository.insertTask(task)
    }
}