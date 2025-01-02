package ie.setu.rugbygame.ui.screens.results

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.rugbygame.data.model.RugbyGameModel
import ie.setu.rugbygame.firebase.services.AuthService
import ie.setu.rugbygame.firebase.services.FirestoreService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ResultsViewModel @Inject
constructor(private val repository: FirestoreService,
            private val authService: AuthService
) : ViewModel() {
    private val _games
            = MutableStateFlow<List<RugbyGameModel>>(emptyList())
    val uiGames: StateFlow<List<RugbyGameModel>>
            = _games.asStateFlow()
    var iserror = mutableStateOf(false)
    var isloading = mutableStateOf(false)
    var error = mutableStateOf(Exception())

    init { getGames() }

    fun getGames() {
        viewModelScope.launch {
            try {
                isloading.value = true
                repository.getAll(authService.email!!).collect { items ->
                    _games.value = items
                    iserror.value = false
                    isloading.value = false
                }
                Timber.i("DVM RVM = : ${_games.value}")
            }
            catch(e:Exception) {
                iserror.value = true
                isloading.value = false
                error.value = e
                Timber.i("RVM Error ${e.message}")
            }
        }
    }

    fun deleteGames(game: RugbyGameModel)
            = viewModelScope.launch {
        repository.delete(authService.email!!,game._id)
    }
}