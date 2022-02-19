package uz.behadllc.bookreaderfirebase.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.behadllc.bookreaderfirebase.database.entitiy.BookModelDatabase

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBookDatabase(bookModelDatabase: BookModelDatabase)

    @Query("SELECT * FROM book_table")
    suspend fun getAllBooksDatabase() : List<BookModelDatabase>

}