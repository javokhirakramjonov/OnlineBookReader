package uz.gita.bookapp.ui.viewModel.viewModelImpl

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.bookapp.data.model.BookModel
import uz.gita.bookapp.domain.booksRepository.BooksRepository
import uz.gita.bookapp.ui.viewModel.ReadingScreenVM
import javax.inject.Inject

@HiltViewModel
class ReadingScreenVMImpl @Inject constructor(
    private val repository: BooksRepository
) : ViewModel(), ReadingScreenVM {
    override val bookLD = MutableLiveData<BookModel>()
    override val errorLD = MutableLiveData<String>()
    override val loadLD = MutableLiveData<Boolean>()
    override val likeDislikeLD = MutableLiveData<BookModel>()
    override val refreshLD = MutableLiveData<Boolean>()
    override val likeLD = MutableLiveData<Boolean>()

    override fun setLoadProgress(progress: Boolean) {
        loadLD.value = progress
    }

    override fun getBook(context: Context, book: BookModel) {
        setLoadProgress(true)
        repository.downloadBookByURL(context, book).onEach {
            it.onSuccess { book ->
                bookLD.value = book
            }
            it.onFailure { error ->
                errorLD.value = error.message
            }
        }.launchIn(viewModelScope)
    }

    override fun setProgress(book: BookModel) {

        refreshLD.value = book.progress == 0

        repository.setProgress(book).onEach {
            it.onFailure { error ->
                errorLD.value = error.message
            }
        }.launchIn(viewModelScope)
    }

    override fun getLikeDislike(book: BookModel) {
        repository.getLikeDislike(book).onEach {
            it.onSuccess { book ->
                likeDislikeLD.value = book
            }
        }.launchIn(viewModelScope)
    }

    override fun setLikeDislike(like: Boolean, book: BookModel) {
        var changedState = false
        if (like) {
            if (!book.like!!) {
                changedState = true
                book.rating!!.likes = book.rating!!.likes!! + 1
                if (book.dislike!!)
                    book.rating!!.dislikes = book.rating!!.dislikes!! - 1
            }
        } else {
            if (!book.dislike!!) {
                changedState = true
                book.rating!!.dislikes = book.rating!!.dislikes!! + 1
                if (book.like!!)
                    book.rating!!.likes = book.rating!!.likes!! - 1
            }
        }
        if (changedState) {
            book.rating!!.id = book.id
            repository.updateRating(book.rating!!).onEach {
                it.onSuccess {
                    book.like = like
                    book.dislike = !like
                    repository.setLikeDislike(book).onEach { result ->
                        result.onSuccess {
                            likeLD.value = like
                        }
                    }.launchIn(viewModelScope)
                }
                it.onFailure {

                }
            }.launchIn(viewModelScope)
        }
    }
}