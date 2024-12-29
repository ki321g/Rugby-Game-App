package ie.setu.rugbygame.ui.screens.game

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.rugbygame.data.model.RugbyGameModel
import ie.setu.rugbygame.firebase.services.AuthService
import ie.setu.rugbygame.firebase.services.FirestoreService
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject
constructor(private val repository: FirestoreService,
            private val authService: AuthService)
    : ViewModel() {
    var isErr = mutableStateOf(false)
    var error = mutableStateOf(Exception())
    var isLoading = mutableStateOf(false)

    fun insert(game: RugbyGameModel) =
        viewModelScope.launch {
            try {
                isLoading.value = true
                repository.insert(authService.email!!,game)
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
