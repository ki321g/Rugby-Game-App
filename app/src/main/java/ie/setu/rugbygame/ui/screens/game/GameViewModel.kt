package ie.setu.rugbygame.ui.screens.game

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

//    private val _games
//            = MutableStateFlow<List<RugbyGameModel>>(emptyList())
//    val uiLatestGame: StateFlow<List<RugbyGameModel>>
//            = _games.asStateFlow()
//    var iserror = mutableStateOf(false)
//    var isloadingGetGames = mutableStateOf(false)
//    var error = mutableStateOf(Exception())

    private val _isGameStarted = MutableStateFlow(false)
    var isGameStarted: StateFlow<Boolean> = _isGameStarted.asStateFlow()

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

    fun startGame() {
        _isGameStarted.value = true
    }

//    fun getLatestGames() {
//        viewModelScope.launch {
//            try {
//                isloadingGetGames.value = true
//                repository.getLatest(authService.email!!).collect { items ->
//                    _games.value = items
//                    iserror.value = false
//                    isloadingGetGames.value = false
//                }
//                Timber.i("DVM RVM = : ${_games.value}")
//            }
//            catch(e:Exception) {
//                iserror.value = true
//                isloadingGetGames.value = false
//                error.value = e
//                Timber.i("RVM Error ${e.message}")
//            }
//        }
//    }

}