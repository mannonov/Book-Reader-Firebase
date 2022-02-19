package uz.behadllc.bookreaderfirebase.ui.favorite

import uz.behadllc.bookreaderfirebase.database.database.BookDatabase
import uz.behadllc.bookreaderfirebase.database.entitiy.BookModelDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepository @Inject constructor(val bookDatabase: BookDatabase) {

    suspend fun addBookDatabase(bookModelDatabase: BookModelDatabase) =
        bookDatabase.bookDao().addBookDatabase(bookModelDatabase)

    suspend fun getAllBookDatabase():List<BookModelDatabase> = bookDatabase.bookDao().getAllBooksDatabase()

}