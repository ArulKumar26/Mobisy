package com.mobisy.claims.di

import android.content.Context
import androidx.room.Room
import com.mobisy.claims.data.db.ClaimDao
import com.mobisy.claims.data.db.ClaimDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ClaimDatabase {
        return Room
            .databaseBuilder(context, ClaimDatabase::class.java, "claim_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideClaimDao(claimDatabase: ClaimDatabase): ClaimDao = claimDatabase.claimDao()
}