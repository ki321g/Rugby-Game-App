package ie.setu.rugbygame.ui.screens.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.rugbygame.data.model.DonationModel
import ie.setu.rugbygame.firebase.services.AuthService
import ie.setu.rugbygame.firebase.services.FirestoreService
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject
constructor(private val repository: FirestoreService,
            private val authService: AuthService,
            savedStateHandle: SavedStateHandle
) : ViewModel() {

    var donation = mutableStateOf(DonationModel())
    val id: String = checkNotNull(savedStateHandle["id"])
    var isErr = mutableStateOf(false)
    var error = mutableStateOf(Exception())
    var isLoading = mutableStateOf(false)

    init {
        viewModelScope.launch {
            try {
                isLoading.value = true
                donation.value = repository.get(authService.email!!,id)!!
                isErr.value = false
                isLoading.value = false
            } catch (e: Exception) {
                isErr.value = true
                error.value = e
                isLoading.value = false
            }
        }
    }

    fun updateDonation(donation: DonationModel) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                repository.update(authService.email!!,donation)
                isErr.value = false
                isLoading.value = false
            } catch (e: Exception) {
                isErr.value = true
                error.value = e
                isLoading.value = false
            }
        }
    }
}

