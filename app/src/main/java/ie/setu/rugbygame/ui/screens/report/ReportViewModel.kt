package ie.setu.rugbygame.ui.screens.report

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.rugbygame.data.model.DonationModel
import ie.setu.rugbygame.data.api.RetrofitRepository
import ie.setu.rugbygame.firebase.services.AuthService
import ie.setu.rugbygame.firebase.services.FirestoreService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject
constructor(private val repository: FirestoreService,
            private val authService: AuthService
) : ViewModel() {
    private val _donations
            = MutableStateFlow<List<DonationModel>>(emptyList())
    val uiDonations: StateFlow<List<DonationModel>>
            = _donations.asStateFlow()
    var iserror = mutableStateOf(false)
    var isloading = mutableStateOf(false)
    var error = mutableStateOf(Exception())

    init { getDonations() }

    fun getDonations() {
        viewModelScope.launch {
            try {
                isloading.value = true
                repository.getAll(authService.email!!).collect { items ->
                    _donations.value = items
                    iserror.value = false
                    isloading.value = false
                }
                Timber.i("DVM RVM = : ${_donations.value}")
            }
            catch(e:Exception) {
                iserror.value = true
                isloading.value = false
                error.value = e
                Timber.i("RVM Error ${e.message}")
            }
        }
    }

    fun deleteDonation(donation: DonationModel)
            = viewModelScope.launch {
        repository.delete(authService.email!!,donation._id)
    }
}

