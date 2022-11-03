package uz.gita.bookapp.data.model

import java.io.Serializable

data class BookModel(
    val id: Int? = 0,
    val name: String? = "",
    val author: String? = "",
    val size: Int? = 0,
    val reference: String? = "",
    val image: String? = "",

    var localReference: String? = "",
    var progress: Int? = 0,
    var rating: RatingModel? = RatingModel(
        id,
        0,
        0
    ),
    var like: Boolean? = false,
    var dislike: Boolean? = false
) : Serializable