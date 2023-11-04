package com.practice.board.domain.usecase

import com.practice.board.domain.model.Task
import com.practice.board.domain.model.TrackingTime

class GetTrackingTimeUseCase {

    operator fun invoke(task: Task): TrackingTime {
        var millis = 0L

        task.trackingPeriods.forEach {
            millis += if (it.second != 0L) {
                it.second - it.first
            }
            else {
                System.currentTimeMillis() - it.first
            }
        }

        return TrackingTime(millis)
    }
}