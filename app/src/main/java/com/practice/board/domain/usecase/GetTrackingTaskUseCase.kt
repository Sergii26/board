package com.practice.board.domain.usecase

import com.practice.board.domain.model.Task
import com.practice.board.domain.repository.TaskRepository
import timber.log.Timber

class GetTrackingTaskUseCase(
    private val taskRepository: TaskRepository,
    private val getTrackingStateUseCase: GetTrackingStateUseCase
) {

    suspend operator fun invoke(): Task? {
        Timber.d("start to find")
        return taskRepository.getAllTasks().filter { getTrackingStateUseCase(it).also {
            Timber.d("tracking state: $it")
        } }.run {
            if (size < 2) firstOrNull()
            else throw IllegalStateException("Several tasks are tracking.")
         }
    }
}