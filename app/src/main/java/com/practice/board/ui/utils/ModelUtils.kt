package com.practice.board.ui.utils

import com.practice.board.domain.model.Task
import com.practice.board.ui.model.TaskUi

fun Task.toTaskUi(isTracking: Boolean): TaskUi {
    return TaskUi(title, description, priority, state, trackingPeriods, uid, isTracking)
}

fun TaskUi.toTask(): Task {
    return Task(title, description, priority, state, trackingPeriods, uid)
}