package com.practice.board.ui.screens.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.board.domain.model.Task
import com.practice.board.domain.model.TrackingState
import com.practice.board.domain.repository.TaskRepository
import com.practice.board.domain.usecase.GetTrackingStateUseCase
import com.practice.board.domain.usecase.GetTrackingTaskUseCase
import com.practice.board.domain.usecase.GetTrackingTimeUseCase
import com.practice.board.domain.usecase.UpdateTrackingStateUseCase
import com.practice.board.ui.model.TaskUi
import com.practice.board.ui.utils.toTask
import com.practice.board.ui.utils.toTaskUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrackingTimeUseCase: GetTrackingTimeUseCase,
    private val getTrackingStateUseCase: GetTrackingStateUseCase,
    private val getTrackingTaskUseCase: GetTrackingTaskUseCase,
    private val updateTrackingStateUseCase: UpdateTrackingStateUseCase,
    private val taskRepository: TaskRepository
): ViewModel() {
    val actions = listOf(Action.AddTask)

    private val _tasks = mutableStateOf<List<TaskUi>>(emptyList())
    val tasks: State<List<TaskUi>> = _tasks

    private val _trackingTask = mutableStateOf<TaskUi?>(null)
    val trackingTask: State<TaskUi?> = _trackingTask

    private val _trackingTime = mutableStateOf<String?>(null)
    val trackingTime: State<String?> = _trackingTime

    private var timerJob: Job? = null

    init {
        viewModelScope.launch {
            getTrackingTaskUseCase()?.let {
                _trackingTask.value = it.toTaskUi(getTrackingStateUseCase(it))
                startTrackingTimer(it)
            }

            taskRepository.observeTasks()
                .onEach { tasks ->
                    _tasks.value = tasks.map { it.toTaskUi(getTrackingStateUseCase(it)) }
                }
                .collect()
        }
    }

    fun onTrackingChanged(task: TaskUi, isTracking: Boolean) {
        Timber.d("onTrackingChanged, task: ${task.title}, new state: ${isTracking}")
        viewModelScope.launch {
            if (isTracking) {
                _trackingTask.value?.let { trackingTask ->
                    if ((trackingTask.uid != task.uid) && getTrackingStateUseCase(trackingTask.toTask())) {
                        updateTrackingStateUseCase(trackingTask.toTask(), TrackingState.Finished)
                    }
                }

                updateTrackingStateUseCase(task.toTask(), TrackingState.Started)
                val updated = taskRepository.getAllTasks().map { it.toTaskUi(getTrackingStateUseCase(it)) }
                updated.find { it.uid == task.uid }?.let {
                    _trackingTask.value = it
                    startTrackingTimer(it.toTask())
                }
            } else {
                _trackingTask.value = null
                timerJob?.cancel()
                updateTrackingStateUseCase(task.toTask(), TrackingState.Finished)
                _tasks.value = taskRepository.getAllTasks().map { it.toTaskUi(getTrackingStateUseCase(it)) }
            }
        }
    }

    private fun startTrackingTimer(trackingTask: Task) {
        timerJob?.cancel()
        timerJob = viewModelScope.launch() {
            while (true) {
                delay(TimeUnit.SECONDS.toMillis(1))
                _trackingTime.value = getTrackingTimeUseCase(trackingTask).getTime()
            }
        }
    }
}