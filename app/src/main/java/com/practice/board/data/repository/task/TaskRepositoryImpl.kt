package com.practice.board.data.repository.task

import com.practice.board.data.room.TaskDto
import com.practice.board.data.room.TaskDao
import com.practice.board.domain.repository.TaskRepository
import com.practice.board.domain.model.Priority
import com.practice.board.domain.model.Task
import com.practice.board.domain.model.TaskState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl(
    private val taskDao: TaskDao
): TaskRepository {

    override suspend fun getAllTasks(): List<Task> {
        return taskDao.getAllTasks().map { it.toTask() }
    }

    override suspend fun observeTasks(): Flow<List<Task>> {
        return taskDao.observeTasks().map { tasks ->
            tasks.map { it.toTask() }
        }
    }

    override suspend fun insertTask(task: Task) {
        taskDao.insertTask(task.toTaskDto())
    }

    override suspend fun updateTask(task: Task) {
        taskDao.updateTask(task.toTaskDto())
    }

    private fun TaskDto.toTask(): Task {
        val trackingPeriods = mutableListOf<Pair<Long, Long>>()
        trackStartedTimes.forEachIndexed { index, value ->
            if (trackFinishedTimes.size >= index + 1) {
                trackingPeriods.add(value to trackFinishedTimes[index])
            } else {
                trackingPeriods.add(value to 0L)
            }
        }

        return Task(title, description, Priority.fromCode(priorityId), TaskState.fromCode(state), trackingPeriods, uid)
    }

    private fun Task.toTaskDto(): TaskDto {
        val startedTimes = trackingPeriods.map { it.first }
        val endedTimes = trackingPeriods.map { it.second }

        return TaskDto(title, description, priority.code, state.code, startedTimes, endedTimes, uid)
    }
}