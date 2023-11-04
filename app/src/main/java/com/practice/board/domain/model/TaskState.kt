package com.practice.board.domain.model

enum class TaskState(val code: Int) {
    Open(0),
    InProgress(1),
    OnReview(2),
    Closed(3),
    Unknown(4);

    companion object {
        fun fromCode(code: Int): TaskState {
            return values().find { it.code == code } ?: Unknown
        }
    }
}