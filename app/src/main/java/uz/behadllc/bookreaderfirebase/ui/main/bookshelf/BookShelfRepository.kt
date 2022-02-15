package uz.behadllc.bookreaderfirebase.ui.main.bookshelf

import android.util.Log
import uz.behadllc.bookreaderfirebase.model.book.Book
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookShelfRepository @Inject constructor(
    private val bookCollection: CollectionReference
) {
    suspend fun getAllBooks(): List<Book> {
        return try {
            bookCollection.get().await().toObjects(Book::class.java)
        } catch (e: Exception) {
            Log.e(TAG, "${e.message}")
            emptyList()
        }
    }

    companion object {
        const val TAG = "Book Shelf Repository"
    }
}