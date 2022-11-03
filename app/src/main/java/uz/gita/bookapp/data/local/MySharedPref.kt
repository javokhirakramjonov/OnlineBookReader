package uz.gita.bookapp.data.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import uz.gita.bookapp.data.model.BookModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MySharedPref @Inject constructor(@ApplicationContext context: Context) {
    private val sharedPreferences = context.getSharedPreferences("DATA", Context.MODE_PRIVATE)

    fun setProgress(id: Int, progress: Int) {
        sharedPreferences.edit().putInt(id.toString(), progress).apply()
    }

    fun getProgress(id: Int): Int {
        return sharedPreferences.getInt(id.toString(), 0)
    }

    fun setLikeDislike(book: BookModel) {
        if (book.like == true) {
            sharedPreferences.edit().putBoolean("${book.id}like", true).apply()
            sharedPreferences.edit().putBoolean("${book.id}dislike", false).apply()
        } else if (book.dislike == true) {
            sharedPreferences.edit().putBoolean("${book.id}like", false).apply()
            sharedPreferences.edit().putBoolean("${book.id}dislike", true).apply()
        }
    }

    fun getLikeDislike(book: BookModel): BookModel {
        book.like = sharedPreferences.getBoolean("${book.id}like", false)
        book.dislike = sharedPreferences.getBoolean("${book.id}dislike", false)
        return book
    }
}