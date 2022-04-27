package com.mobisy.claims.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mobisy.claims.data.db.DataConverter

@Entity(tableName = "claim_data")
class ResultClaimData {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "claim_date")
    var claimDate: String? = null

    @ColumnInfo(name = "claim_type_id")
    var claimTypeId: String? = null

    @ColumnInfo(name = "claim_type")
    var claimType: String? = null

    @TypeConverters(DataConverter::class)
    @ColumnInfo(name = "claim_field_option")
    var claimFieldoption: ArrayList<ResultClaimTypeOption> = arrayListOf()
}