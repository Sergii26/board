package com.practice.board.ui.model

import com.practice.board.domain.model.Priority
import com.practice.board.domain.model.TaskState

data class TaskUi(
    val title: String,
    val description: String,
    val priority: Priority,
    val state: TaskState,
    var trackingPeriods: List<Pair<Long, Long>> = emptyList(), // first: start time, second: end time.
    val uid: Int = 0,
    var isTracking: Boolean
)