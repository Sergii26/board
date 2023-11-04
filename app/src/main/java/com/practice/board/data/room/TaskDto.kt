package com.practice.board.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskDto(
    val title: String,
    val description: String,
    val priorityId: Int,
    val state: Int,
    var trackStartedTimes: List<Long>,
    var trackFinishedTimes: List<Long>,
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
)




