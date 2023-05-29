package com.zhixue.lite.core.database.di

import android.content.Context
import androidx.room.Room
import com.zhixue.lite.core.database.ZhibanDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DB_NAME = "zhiban-database"

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideZhiBanDatabase(
        @ApplicationContext context: Context
    ): ZhibanDatabase {
        return Room
            .databaseBuilder(context, ZhibanDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
}