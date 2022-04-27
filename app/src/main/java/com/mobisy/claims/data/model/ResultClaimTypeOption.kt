package com.mobisy.claims.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ResultClaimTypeOption {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "checked_id")
    var checkedId: String? = null

    @ColumnInfo(name = "checked_name")
    var checkedName: String? = null

    @ColumnInfo(name = "entered_value_id")
    var enteredValueId: String? = null

    @ColumnInfo(name = "entered_value")
    var enteredValue: String? = null
}