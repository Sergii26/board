package com.practice.board.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    suspend fun getAllTasks(): List<TaskDto>

    @Query("SELECT * FROM tasks")
    fun observeTasks(): Flow<List<TaskDto>>

    @Insert
    suspend fun insertTask(task: TaskDto)

    @Update
    suspend fun updateTask(task: TaskDto)
}