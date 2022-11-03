package uz.gita.bookapp.ui.viewModel.viewModelImpl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.bookapp.ui.viewModel.SplashScreenVM

class SplashScreenVMImpl : ViewModel(), SplashScreenVM {
    override val goMainScreenLD = MutableLiveData<Unit>()

    override fun goMainScreen() {
        goMainScreenLD.postValue(Unit)
    }

}