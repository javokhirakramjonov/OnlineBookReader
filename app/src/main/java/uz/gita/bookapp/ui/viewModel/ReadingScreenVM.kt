package uz.gita.bookapp.ui.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import uz.gita.bookapp.data.model.BookModel

interface ReadingScreenVM {
    val bookLD: LiveData<BookModel>
    val errorLD: LiveData<String>
    val loadLD: LiveData<Boolean>
    val likeDislikeLD: LiveData<BookModel>
    val refreshLD: LiveData<Boolean>
    val likeLD: LiveData<Boolean>

    fun setLoadProgress(progress: Boolean)
    fun getBook(context: Context, book: BookModel)
    fun setProgress(book: BookModel)
    fun getLikeDislike(book: BookModel)
    fun setLikeDislike(like: Boolean, book: BookModel)
}