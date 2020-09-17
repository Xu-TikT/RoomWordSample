package com.tikt.roomwordsample

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_table")
data class Word(
    @ColumnInfo(name = "word") val word: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var uid: Int = 0

}