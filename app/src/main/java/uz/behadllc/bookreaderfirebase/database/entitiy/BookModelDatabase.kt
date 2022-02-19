package uz.behadllc.bookreaderfirebase.database.entitiy

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_table")
data class BookModelDatabase(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val bookUrl: String,
    val iconUrl: String,
    val name: String,
    val desc: String,
    val authorName: String,
    val docId: String,
    val pageCount: Int,
    val reviewCount: Int,
    val rating: Float,
)