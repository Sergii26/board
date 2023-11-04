package com.practice.board.domain.model

enum class Priority(val code: Int) {
    Low(0),
    Medium(1),
    High(2),
    Unknown(-1);

    companion object {
        fun fromCode(code: Int): Priority {
            return values().find { it.code == code } ?: Unknown
        }
    }
}