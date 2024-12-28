package ie.setu.rugbygame.ui.screens.donate

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.rugbygame.data.model.DonationModel
import ie.setu.rugbygame.data.api.RetrofitRepository
import ie.setu.rugbygame.firebase.services.AuthService
import ie.setu.rugbygame.firebase.services.FirestoreService
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DonateViewModel @Inject
constructor(private val repository: FirestoreService,
            private val authService: AuthService)
    : ViewModel() {
    var isErr = mutableStateOf(false)
    var error = mutableStateOf(Exception())
    var isLoading = mutableStateOf(false)

//    fun insert(donation: DonationModel)
//            = viewModelScope.launch {
//                repository.insert(donation)
//    }

    fun insert(donation: DonationModel) =
        viewModelScope.launch {
        try {
            isLoading.value = true
            repository.insert(authService.email!!,donation)
            isErr.value = false
            isLoading.value = false
        } catch (e: Exception) {
            isErr.value = true
            error.value = e
            isLoading.value = false
        }
            Timber.i("DVM Insert Message = : ${error.value.message} and isError ${isErr.value}")
    }
}
