package uz.gita.bookapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.gita.bookapp.R
import uz.gita.bookapp.data.model.BookModel
import uz.gita.bookapp.databinding.ItemBookBinding

/*
class BookAdapter : ListAdapter<BookModel, BookAdapter.ViewHolder>(BookModelDiffUtil) {

    private var bookSelectListener: ((book: BookModel) -> Unit)? = null

    fun setSelectListener(block: (BookModel) -> Unit) {
        bookSelectListener = block
    }

    private object BookModelDiffUtil : DiffUtil.ItemCallback<BookModel>() {
        override fun areItemsTheSame(oldItem: BookModel, newItem: BookModel): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: BookModel, newItem: BookModel): Boolean {
            return false
        }
    }

    inner class ViewHolder(private val binding: ItemBookBinding, private val context: Context) : RecyclerView
    .ViewHolder(
        binding
            .root
    ) {

        init {
            binding.apply {
                image.setOnClickListener {
                    bookSelectListener?.invoke(getItem(absoluteAdapterPosition))
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind() {
            getItem(absoluteAdapterPosition).apply {
                Glide.with(context)
                    .load(image)
                    .placeholder(R.drawable.placeholder)
                    .into(binding.image)

                if (name!!.length > 20)
                    binding.name.text = name.substring(0, 17) + "..."
                else
                    binding.name.text = name
                if (author!!.length > 20)
                    binding.author.text = author.substring(0, 17) + "..."
                else
                    binding.author.text = author
                binding.percent.text = (100 * progress!! / size!!).toString() + "%"
                binding.progress.progress = (100 * progress!! / size)
                rating?.apply {
                    binding.rating.rating = (likes!!.toFloat() / (likes!! + dislikes!!)) * 5
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookAdapter.ViewHolder {
        return ViewHolder(ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false), parent.context)
    }

    override fun onBindViewHolder(holder: BookAdapter.ViewHolder, position: Int) {
        holder.bind()
    }
}*/

class BookAdapter : RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    private var bookSelectListener: ((book: BookModel) -> Unit)? = null
    private var books = ArrayList<BookModel>()

    fun submitList(list: List<BookModel>) {
        books.clear()
        books.addAll(list)
        notifyDataSetChanged()
    }

    fun setSelectListener(block: (BookModel) -> Unit) {
        bookSelectListener = block
    }

    inner class ViewHolder(private val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                image.setOnClickListener {
                    bookSelectListener?.invoke(books[absoluteAdapterPosition])
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind() {
            books[absoluteAdapterPosition].apply {
                Glide.with(binding.root)
                    .load(image)
                    .placeholder(R.drawable.placeholder)
                    .into(binding.image)

                if (name!!.length > 20)
                    binding.name.text = name.substring(0, 17) + "..."
                else
                    binding.name.text = name
                if (author!!.length > 20)
                    binding.author.text = author.substring(0, 17) + "..."
                else
                    binding.author.text = author
                binding.percent.text = (100 * progress!! / size!!).toString() + "%"
                binding.progress.progress = (100 * progress!! / size)
                rating?.apply {
                    binding.rating.rating = (likes!!.toFloat() / (likes!! + dislikes!!)) * 5
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookAdapter.ViewHolder {
        return ViewHolder(ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BookAdapter.ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return books.size
    }

}
