package uz.gita.bookapp.ui.screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.bookapp.R
import uz.gita.bookapp.ui.viewModel.SplashScreenVM
import uz.gita.bookapp.ui.viewModel.viewModelImpl.SplashScreenVMImpl

@AndroidEntryPoint
class StartScreen : Fragment(R.layout.screen_splash) {

    private val viewModel: SplashScreenVM by viewModels<SplashScreenVMImpl>()

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.goMainScreen()
        viewModel.goMainScreenLD.observe(this@StartScreen, goMainScreenObserver)
    }

    private val goMainScreenObserver = Observer<Unit> {
        findNavController().navigate(R.id.action_startScreen_to_mainScreen)
    }

}