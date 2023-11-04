package com.practice.board.di

import android.content.Context
import com.practice.board.data.room.TaskDatabase
import com.practice.board.domain.repository.TaskRepository
import com.practice.board.data.repository.task.TaskRepositoryImpl
import com.practice.board.data.room.TaskDao
import com.practice.board.domain.usecase.AddTaskUseCase
import com.practice.board.domain.usecase.GetTrackingStateUseCase
import com.practice.board.domain.usecase.GetTrackingTaskUseCase
import com.practice.board.domain.usecase.GetTrackingTimeUseCase
import com.practice.board.domain.usecase.UpdateTrackingStateUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository {
        return TaskRepositoryImpl(taskDao)
    }

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): TaskDao {
        return TaskDatabase.getInstance(context).taskDao()
    }

    @Provides
    fun provideGetTrackingStateUseCase(): GetTrackingStateUseCase {
        return GetTrackingStateUseCase()
    }

    @Provides
    fun provideGetTrackingTaskUseCase(
        taskRepository: TaskRepository,
        getTrackingStateUseCase: GetTrackingStateUseCase
    ): GetTrackingTaskUseCase {
        return GetTrackingTaskUseCase(taskRepository, getTrackingStateUseCase)
    }

    @Provides
    fun provideGetTrackingTimeUseCase(): GetTrackingTimeUseCase {
        return GetTrackingTimeUseCase()
    }

    @Provides
    fun provideUpdateTrackingTimeUseCase(taskRepository: TaskRepository): UpdateTrackingStateUseCase {
        return UpdateTrackingStateUseCase(taskRepository)
    }

    @Provides
    fun provideAddTaskUseCase(taskRepository: TaskRepository): AddTaskUseCase {
        return AddTaskUseCase(taskRepository)
    }
}