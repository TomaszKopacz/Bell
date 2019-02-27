package com.example.adam.myapplication.data.objects

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class Doctor {

    companion object {
        private const val SPECIALIZATION = "specialization"
        private const val NAME = "name"
        private const val LOCATION = "location"
    }

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = SPECIALIZATION)
    var specialization: String? = null

    @ColumnInfo(name = NAME)
    var name: String? = null

    @ColumnInfo(name = LOCATION)
    var location: String? = null
}