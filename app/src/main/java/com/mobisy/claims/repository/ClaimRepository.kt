package com.mobisy.claims.repository

import com.mobisy.claims.data.db.ClaimDao
import com.mobisy.claims.data.model.ResultClaimData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ClaimRepository @Inject constructor(
    private val claimDao: ClaimDao
) {

    suspend fun saveClaims(resultClaimData: ResultClaimData) {
        withContext(Dispatchers.IO) {
            claimDao.insertData(resultClaimData)
        }
    }

    suspend fun getClaims(): List<ResultClaimData> = claimDao.getAll()

}

