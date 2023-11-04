package com.practice.board.domain.usecase

import com.practice.board.domain.model.Task
import com.practice.board.domain.model.TrackingState
import com.practice.board.domain.repository.TaskRepository

class UpdateTrackingStateUseCase(private val taskRepository: TaskRepository) {

    suspend operator fun invoke(task: Task, state: TrackingState) {
        when (state) {
            TrackingState.Finished -> {
                if (task.isTrackingStarted()) {
                    task.finishTracking()
                    taskRepository.updateTask(task)
                } else {
                    throw IllegalStateException("Try to finish tracking when it is not started.")
                }
            }
            TrackingState.Started -> {
                if (task.isTrackingFinished()) {
                    task.startTracking()
                    taskRepository.updateTask(task)
                } else {
                    throw IllegalStateException("Try to start tracking when it is not ended.")
                }
            }
        }
    }

    private fun Task.isTrackingStarted(): Boolean {
        return if (trackingPeriods.isNotEmpty()) trackingPeriods.last().second == 0L
            else false
    }

    private fun Task.isTrackingFinished(): Boolean {
        return if (trackingPeriods.isNotEmpty()) trackingPeriods.last().second != 0L
            else true
    }

    private fun Task.startTracking() {
        val updatedPeriods = ArrayList(trackingPeriods)
        updatedPeriods.add(Pair(System.currentTimeMillis(), 0L))
        trackingPeriods = updatedPeriods
    }

    private fun Task.finishTracking() {
        val updatedPeriods = ArrayList(trackingPeriods)
        updatedPeriods.add(Pair(updatedPeriods.removeLast().first, System.currentTimeMillis()))
        trackingPeriods = updatedPeriods
    }
}