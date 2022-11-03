package uz.gita.bookapp.ui.viewModel

import androidx.lifecycle.LiveData

interface SplashScreenVM {
    val goMainScreenLD: LiveData<Unit>

    fun goMainScreen()
}