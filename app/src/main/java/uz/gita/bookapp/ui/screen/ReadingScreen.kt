package uz.gita.bookapp.ui.screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.bookapp.R
import uz.gita.bookapp.data.model.BookModel
import uz.gita.bookapp.databinding.ScreenReadingBinding
import uz.gita.bookapp.ui.viewModel.ReadingScreenVM
import uz.gita.bookapp.ui.viewModel.viewModelImpl.ReadingScreenVMImpl
import java.io.File

@AndroidEntryPoint
class ReadingScreen : Fragment(R.layout.screen_reading) {
    private val viewModel: ReadingScreenVM by viewModels<ReadingScreenVMImpl>()
    private val binding by viewBinding(ScreenReadingBinding::bind)
    private lateinit var book: BookModel

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        back.setOnClickListener {
            findNavController().popBackStack()
        }

        book = requireArguments().getSerializable("book") as BookModel

        if(book.like!!)
            like.setImageResource(R.drawable.ic_like_filled)
        else
            like.setImageResource(R.drawable.ic_like_svg)

        if(book.dislike!!)
            dislike.setImageResource(R.drawable.ic_dislike_filled)
        else
            dislike.setImageResource(R.drawable.ic_dislike_svg)

        if(book.name!!.length > 20)
            binding.name.text = book.name!!.substring(0, 17) + "..."
        else
            binding.name.text = book.name

        viewModel.getBook(requireContext(), book)

        viewModel.likeLD.observe(viewLifecycleOwner, likeObserver)
        viewModel.refreshLD.observe(viewLifecycleOwner, refreshObserver)
        viewModel.loadLD.observe(viewLifecycleOwner, loadObserver)
        viewModel.bookLD.observe(viewLifecycleOwner, bookObserver)
    }

    private val likeObserver = Observer<Boolean> {
        book.like = it
        book.dislike = !it
        binding.apply{
            if(it) {
                like.setImageResource(R.drawable.ic_like_filled)
                dislike.setImageResource(R.drawable.ic_dislike_svg)
            } else {
                like.setImageResource(R.drawable.ic_like_svg)
                dislike.setImageResource(R.drawable.ic_dislike_filled)
            }
        }
    }

    private val refreshObserver = Observer<Boolean> {
        binding.refresh.isEnabled = it
    }

    private val loadObserver = Observer<Boolean> {
        binding.refresh.isRefreshing = it
    }

    @SuppressLint("SetTextI18n")
    private val bookObserver = Observer<BookModel> {

        binding.apply {
            like.setOnClickListener { like ->
                viewModel.setLikeDislike(true, it)
            }
            dislike.setOnClickListener { dislike ->
                viewModel.setLikeDislike(false, it)
            }
            refresh.setOnRefreshListener {
                viewModel.getBook(requireContext(), it)
            }
        }

        binding.pdfViewer.fromFile(File(requireContext().filesDir, it.localReference!!))
            .defaultPage(it.progress!!)
            .onLoad {
                viewModel.setLoadProgress(false)
            }
            .onPageChange { page, pageCount ->
                it.progress = page
                viewModel.setProgress(it)
                binding.pager.text = "$page/$pageCount"
            }
            .load()
    }
}