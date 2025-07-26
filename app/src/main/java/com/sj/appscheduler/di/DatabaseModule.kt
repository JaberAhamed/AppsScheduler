package com.sj.appscheduler.di

import android.content.Context
import androidx.room.Room
import com.sj.appscheduler.db.AppDatabase
import com.sj.appscheduler.db.ScheduleDao
import com.sj.appscheduler.repositories.DefaultScheduleRepository
import com.sj.appscheduler.repositories.ScheduleRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindScheduleRepository(repository: DefaultScheduleRepository): ScheduleRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "app_scheduler_db"
    ).build()

    @Provides
    fun provideTaskDao(database: AppDatabase): ScheduleDao = database.appInfoDao()
}
