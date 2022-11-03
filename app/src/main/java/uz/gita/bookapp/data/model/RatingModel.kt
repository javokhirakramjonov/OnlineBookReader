package uz.gita.bookapp.data.model

data class RatingModel(
    var id: Int? = 0,
    var likes: Int? = 0,
    var dislikes: Int? = 0
) {
    override fun toString(): String {
        return "id: $id, likes: $likes, dislikes: $dislikes"
    }
}