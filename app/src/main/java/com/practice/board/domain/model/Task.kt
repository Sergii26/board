package com.practice.board.domain.model

class Task(
    val title: String,
    val description: String,
    val priority: Priority,
    val state: TaskState,
    var trackingPeriods: List<Pair<Long, Long>> = emptyList(), // first: start time, second: end time.
    val uid: Int = 0,
) {

    override fun toString(): String {
        return "[title: ${title}..., " +
                "description: ${description}..., " +
                "priority: ${priority}, " +
                "state: ${state}, " +
                "tracking periods: ${trackingPeriods}]"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Task

        if (title != other.title) return false
        if (description != other.description) return false
        if (priority != other.priority) return false
        if (state != other.state) return false
        if (trackingPeriods != other.trackingPeriods) return false
        if (uid != other.uid) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + (description.hashCode())
        result = 31 * result + (priority.hashCode())
        result = 31 * result + (state.hashCode())
        result = 31 * result + trackingPeriods.hashCode()
        result = 31 * result + uid
        return result
    }
}