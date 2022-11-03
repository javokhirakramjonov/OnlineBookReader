package uz.gita.bookapp.ui.viewModel

import androidx.lifecycle.LiveData
import uz.gita.bookapp.data.model.BookModel
import uz.gita.bookapp.data.model.RatingModel

interface MainScreenVM {
    val booksLD: LiveData<List<BookModel>>
    val errorLD: LiveData<String>
    val goReadingScreenLD: LiveData<BookModel>
    val progressLD: LiveData<BookModel>

    fun goReadingScreen(book: BookModel)
    fun getAllBooks()
    fun getRatings(books: List<BookModel>)
    fun getProgress(books: List<BookModel>)
}