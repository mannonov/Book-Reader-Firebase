package uz.behadllc.bookreaderfirebase.ui.favorite

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.behadllc.bookreaderfirebase.database.entitiy.BookModelDatabase

class FavoriteViewModel @ViewModelInject constructor(val favoriteRepository: FavoriteRepository) :
    ViewModel() {

    private var booksLiveData = MutableLiveData<List<BookModelDatabase>>()

    init {
        getAllBooks()
    }

    private fun getAllBooks() {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                booksLiveData.value = favoriteRepository.getAllBookDatabase()
            }
        }
    }

    fun addBooks(bookModelDatabase: BookModelDatabase) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                favoriteRepository.addBookDatabase(bookModelDatabase = bookModelDatabase)
            }
        }
    }

    fun getAllFavoriteBooks(): LiveData<List<BookModelDatabase>> = booksLiveData

}