package com.practice.board.domain.model

enum class TrackingState  {
    Started,
    Finished;

    companion object {
        fun fromBoolean(isTracking: Boolean): TrackingState {
            return if (isTracking) Started
            else Finished
        }
    }
}