package com.mobisy.claims.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mobisy.claims.data.model.ResultClaimData
import com.mobisy.claims.data.model.ResultClaimTypeOption

@Database(
    entities = [ResultClaimData::class, ResultClaimTypeOption::class],
    version = 1,
    exportSchema = false
)
abstract class ClaimDatabase : RoomDatabase() {
    abstract fun claimDao(): ClaimDao
}