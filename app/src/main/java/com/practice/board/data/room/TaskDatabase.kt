package com.practice.board.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [TaskDto::class], version = 2)
@TypeConverters(Converters::class)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {

        fun getInstance(context: Context): TaskDatabase {
            return createDatabase(context)
        }

        private fun createDatabase(context: Context): TaskDatabase {
            return Room.databaseBuilder(
                context,
                TaskDatabase::class.java,
                "task_database"
            ).build()
        }
    }
}