package ie.setu.rugbygame.ui.components.game

import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ie.setu.rugbygame.R
import ie.setu.rugbygame.data.model.RugbyGameModel
import ie.setu.rugbygame.data.model.fakeGames
import ie.setu.rugbygame.ui.screens.game.GameViewModel
import ie.setu.rugbygame.ui.screens.map.MapViewModel
import ie.setu.rugbygame.ui.screens.results.ResultsViewModel
import ie.setu.rugbygame.ui.theme.RugbyScoreTheme
import timber.log.Timber

@Composable
fun StartButton(
    modifier: Modifier = Modifier,
    game: RugbyGameModel,
    gameViewModel: GameViewModel = hiltViewModel(),
    resultsViewModel: ResultsViewModel = hiltViewModel(),
    mapViewModel: MapViewModel = hiltViewModel(),
    onIsGameStartedChange: (Boolean) -> Unit,
//    onClickGameDetails: (String) -> Unit
) {
    val isGameStarted by gameViewModel.isGameStarted.collectAsState()
    val games = resultsViewModel.uiGames.collectAsState().value
    val context = LocalContext.current

    val isError = gameViewModel.isErr.value
    val error = gameViewModel.error.value
    val locationLatLng = mapViewModel.currentLatLng.collectAsState().value

    LaunchedEffect(mapViewModel.currentLatLng){
        mapViewModel.getLocationUpdates()
    }

    Timber.i("Save Game BUTTON LAT/LNG COORDINATES " +
            "lat/Lng: " + "$locationLatLng ")

    Button(
        modifier = Modifier
            .fillMaxWidth(), // Makes button full width
        onClick = {
                val gameLatLng = game.copy(
                    latitude = locationLatLng.latitude,
                    longitude = locationLatLng.longitude
                )
                gameViewModel.insert(gameLatLng)
                gameViewModel.startGame()
                onIsGameStartedChange(isGameStarted)
//                val latestId = gameViewModel.uiLatestGame.value.firstOrNull()?._id
//                onClickGameDetails(latestId.toString())
//                val latestGame = gameViewModel.getLatestGames()
//                onClickGameDetails(latestGame._ID.toString())
        },
        elevation = ButtonDefaults.buttonElevation(20.dp),
        contentPadding = PaddingValues(
            start = 24.dp,
            end = 24.dp
        )
    ) {
        Icon(Icons.Default.Add, contentDescription = "Save Game")
        Spacer(modifier.width(width = 4.dp))
        Text(
            text = stringResource(R.string.saveButton),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.White
        )
    }

    Timber.i("DVM Button = : ${error.message}")
    //Required to refresh our 'totalDonated'
    if(isError)
        Toast.makeText(context,"Unable to Start Game at this Time...",
            Toast.LENGTH_SHORT).show()
    else
        resultsViewModel.getGames()
}

@Preview(showBackground = true)
@Composable
fun StartButtonPreview() {
    RugbyScoreTheme {
        PreviewStartButton(
            Modifier,
            RugbyGameModel(),
            games = fakeGames.toMutableStateList()
        )
    }
}

@Composable
fun PreviewStartButton(
    modifier: Modifier = Modifier,
    game: RugbyGameModel,
    games: SnapshotStateList<RugbyGameModel>
) {
    Button(
        onClick = {
                games.add(game)
                Timber.i("Game info : $game")
                Timber.i("Game List info : ${games.toList()}")
        },
        elevation = ButtonDefaults.buttonElevation(20.dp),
        contentPadding = PaddingValues(
            start = 24.dp,
            end = 24.dp
        )
    ) {
        Icon(Icons.Default.Add, contentDescription = "Save Game")
        Spacer(modifier.width(width = 4.dp))
        Text(
            text = stringResource(R.string.startButton),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.White
        )
    }
}