package uz.gita.bookapp.ui.screen

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.bookapp.R
import uz.gita.bookapp.checkPermissions
import uz.gita.bookapp.data.model.BookModel
import uz.gita.bookapp.data.model.RatingModel
import uz.gita.bookapp.databinding.ScreenMainBinding
import uz.gita.bookapp.ui.adapter.BookAdapter
import uz.gita.bookapp.ui.viewModel.MainScreenVM
import uz.gita.bookapp.ui.viewModel.viewModelImpl.MainScreenVMImpl

@AndroidEntryPoint
class MainScreen : Fragment(R.layout.screen_main) {

    private val viewModel: MainScreenVM by viewModels<MainScreenVMImpl>()
    private val binding by viewBinding(ScreenMainBinding::bind)
    private val adapter = BookAdapter()

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {

        booksRecyclerView.adapter = adapter
        adapter.setSelectListener {
            requireActivity().checkPermissions(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                viewModel.goReadingScreen(it)
            }
        }

        viewModel.goReadingScreenLD.observe(this@MainScreen, goReadingScreenObserver)
        viewModel.errorLD.observe(this@MainScreen, errorObserver)
        viewModel.booksLD.observe(this@MainScreen, booksObserver)
    }

    private val goReadingScreenObserver = Observer<BookModel> {
        val book = Bundle()
        book.putSerializable("book", it)
        findNavController().navigate(R.id.action_mainScreen_to_readingScreen, book)
    }

    private val errorObserver = Observer<String> {
        Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
    }

    private val booksObserver = Observer<List<BookModel>> {
        adapter.submitList(it)
    }
}