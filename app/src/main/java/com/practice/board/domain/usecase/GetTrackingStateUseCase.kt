package com.practice.board.domain.usecase

import com.practice.board.domain.model.Task

class GetTrackingStateUseCase() {
    operator fun invoke(task: Task): Boolean {
        return task.trackingPeriods.find { it.first != 0L && it.second == 0L } != null
    }
}