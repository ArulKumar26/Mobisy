package com.mobisy.claims.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mobisy.claims.data.model.ResultClaimData

@Dao
interface ClaimDao {

    @Insert
    suspend fun insertData(resultClaimData: ResultClaimData)

    @Query("SELECT * FROM claim_data")
    suspend fun getAll(): List<ResultClaimData>

}