package com.example.adam.myapplication.data.objects

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

import java.util.Date

@Entity
class Task(@field:ColumnInfo(name = TYPE)
           var type: String?, @field:ColumnInfo(name = TIMESTAMP)
           var timestamp: Date?) {

    companion object {

        const val SCORE = "score"
        const val DRUG = "drug"
        const val DOCTOR = "doctor"

        private const val TYPE = "type"
        private const val TIMESTAMP = "timestamp"
        private const val INFO = "info"
        private const val STATUS = "status"
        private const val NOTE = "note"
    }

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = INFO)
    var info: String? = null

    @ColumnInfo(name = STATUS)
    var status: Boolean = false

    @ColumnInfo(name = NOTE)
    var note: String? = null
}