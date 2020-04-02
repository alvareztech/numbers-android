package tech.alvarez.numbers.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "channels")
data class Channel(
    @PrimaryKey
    var id: String,
    var title: String,
    var description: String
)