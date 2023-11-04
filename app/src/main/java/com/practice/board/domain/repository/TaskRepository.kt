package com.practice.board.domain.repository

import com.practice.board.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun getAllTasks(): List<Task>
    suspend fun observeTasks(): Flow<List<Task>>
    suspend fun insertTask(task: Task)
    suspend fun updateTask(task: Task)
}