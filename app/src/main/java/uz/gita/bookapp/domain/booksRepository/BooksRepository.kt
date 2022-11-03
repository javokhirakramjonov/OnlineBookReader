package uz.gita.bookapp.domain.booksRepository

import android.content.Context
import kotlinx.coroutines.flow.Flow
import uz.gita.bookapp.data.model.BookModel
import uz.gita.bookapp.data.model.RatingModel

interface BooksRepository {
    fun getAllBooks(): Flow<Result<List<BookModel>>>
    fun getRatings(): Flow<Result<List<RatingModel>>>
    fun updateRating(rating: RatingModel): Flow<Result<Unit>>
    fun downloadBookByURL(context: Context, book: BookModel): Flow<Result<BookModel>>

    fun setProgress(book: BookModel): Flow<Result<Unit>>
    fun getProgress(books: List<BookModel>): Flow<Result<List<BookModel>>>
    fun setLikeDislike(book: BookModel): Flow<Result<Unit>>
    fun getLikeDislike(book: BookModel): Flow<Result<BookModel>>
}