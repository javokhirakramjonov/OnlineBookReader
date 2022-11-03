package uz.gita.bookapp.ui.viewModel.viewModelImpl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.bookapp.data.model.BookModel
import uz.gita.bookapp.domain.booksRepository.BooksRepository
import uz.gita.bookapp.ui.viewModel.MainScreenVM
import javax.inject.Inject

@HiltViewModel
class MainScreenVMImpl @Inject constructor(
    private val repository: BooksRepository
) : ViewModel(), MainScreenVM {
    override val booksLD = MutableLiveData<List<BookModel>>()
    override val errorLD = MutableLiveData<String>()
    override val goReadingScreenLD = MutableLiveData<BookModel>()
    override val progressLD = MutableLiveData<BookModel>()

    override fun goReadingScreen(book: BookModel) {
        goReadingScreenLD.value = book
    }

    init {
        getAllBooks()
    }

    override fun getAllBooks() {
        repository.getAllBooks().onEach {
            it.onSuccess { books ->
                getRatings(books)
            }
            it.onFailure { error ->
                errorLD.value = error.message
            }
        }.launchIn(viewModelScope)
    }

    override fun getRatings(books: List<BookModel>) {
        repository.getRatings().onEach {
            it.onSuccess { ratings ->
                ratings.forEach { rating ->
                    books[rating.id!! - 1].rating = rating
                }
                getProgress(books)
            }
            it.onFailure { error ->
                errorLD.value = error.message
            }
        }.launchIn(viewModelScope)
    }

    override fun getProgress(books: List<BookModel>) {
        repository.getProgress(books).onEach {
            it.onSuccess { books ->
                booksLD.value = books
            }
        }.launchIn(viewModelScope)
    }
}