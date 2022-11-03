package uz.gita.bookapp.domain.booksRepository.booksRepositoryImpl


import android.content.Context
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import uz.gita.bookapp.data.local.MySharedPref
import uz.gita.bookapp.data.model.BookModel
import uz.gita.bookapp.data.model.RatingModel
import uz.gita.bookapp.domain.booksRepository.BooksRepository
import java.io.File
import javax.inject.Inject

class BooksRepositoryImpl @Inject constructor(
    sharedPreferences: MySharedPref,
    fireStore: FirebaseFirestore,
    firebaseDatabase: FirebaseDatabase,
    firebaseStorage: FirebaseStorage
) : BooksRepository {

    private val books = fireStore.collection("books")
    private val ratings = firebaseDatabase.reference
    private val booksStorage = firebaseStorage.reference.child("books")
    private val local = sharedPreferences

    override fun getAllBooks() = callbackFlow<Result<List<BookModel>>> {
        books.get()
            .addOnSuccessListener {
                val data = it.toObjects(BookModel::class.java)
                trySend(Result.success(data))
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }
        awaitClose {}
    }.flowOn(Dispatchers.IO)

    override fun getRatings() = callbackFlow<Result<List<RatingModel>>> {
        ratings.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val allRatings = ArrayList<RatingModel>()
                snapshot.children.forEach {
                    allRatings.add(it.getValue(RatingModel::class.java)!!)
                }
                trySend(Result.success(allRatings))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(Result.failure(error.toException()))
            }
        })
        awaitClose {

        }
    }.flowOn(Dispatchers.IO)

    override fun updateRating(rating: RatingModel) = callbackFlow<Result<Unit>> {
        ratings.child(rating.id.toString()).setValue(rating)
            .addOnSuccessListener {
                trySend(Result.success(Unit))
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }
        awaitClose {

        }
    }.flowOn(Dispatchers.IO)

    override fun downloadBookByURL(context: Context, book: BookModel) = callbackFlow<Result<BookModel>> {
        if (File(context.filesDir, book.name!!).exists()) {
            book.localReference = book.name
            trySend(Result.success(book))
        } else {
            booksStorage.child(book.reference!!).getFile(File(context.filesDir, book.name))
                .addOnSuccessListener {
                    book.localReference = book.name
                    trySend(Result.success(book))
                }
                .addOnFailureListener {
                    trySend(Result.failure(it))
                }
        }
        awaitClose {

        }
    }.flowOn(Dispatchers.IO)

    override fun setProgress(book: BookModel) = callbackFlow<Result<Unit>> {
        local.setProgress(book.id!!, book.progress!!)
        trySend(Result.success(Unit))
        awaitClose {

        }
    }.flowOn(Dispatchers.IO)

    override fun getProgress(books: List<BookModel>) = callbackFlow<Result<List<BookModel>>> {
        val answer = books as MutableList<BookModel>
        for(i in books.indices) {
            answer[i] = local.getLikeDislike(answer[i])
            answer[i].progress = local.getProgress(answer[i].id!!)
        }
        trySend(Result.success(answer))
        awaitClose {

        }
    }.flowOn(Dispatchers.IO)

    override fun setLikeDislike(book: BookModel) = callbackFlow<Result<Unit>> {
        local.setLikeDislike(book)
        trySend(Result.success(Unit))
        awaitClose {

        }
    }.flowOn(Dispatchers.IO)

    override fun getLikeDislike(book: BookModel) = callbackFlow<Result<BookModel>> {
        val temp = local.getLikeDislike(book)
        trySend(Result.success(temp))
        awaitClose {

        }
    }.flowOn(Dispatchers.IO)

}