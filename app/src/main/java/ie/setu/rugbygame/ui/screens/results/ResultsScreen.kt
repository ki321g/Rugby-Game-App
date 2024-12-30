package ie.setu.rugbygame.ui.screens.results


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ie.setu.rugbygame.R
import ie.setu.rugbygame.data.model.RugbyGameModel
import ie.setu.rugbygame.data.model.fakeGames
import ie.setu.rugbygame.ui.components.general.Centre
import ie.setu.rugbygame.ui.components.general.ShowError
import ie.setu.rugbygame.ui.components.general.ShowLoader
import ie.setu.rugbygame.ui.components.general.ShowRefreshList
import ie.setu.rugbygame.ui.components.results.ResultsText
import ie.setu.rugbygame.ui.components.results.GameCardList
import ie.setu.rugbygame.ui.screens.results.ResultsViewModel
import ie.setu.rugbygame.ui.theme.RugbyScoreTheme
import timber.log.Timber


@Composable
fun ResultsScreen(modifier: Modifier = Modifier,
                  onClickGameDetails: (String) -> Unit,
                 resultsViewModel: ResultsViewModel = hiltViewModel()) {

    val games = resultsViewModel.uiGames.collectAsState().value
    val isError = resultsViewModel.iserror.value
    val error = resultsViewModel.error.value
    val isLoading = resultsViewModel.isloading.value

    Timber.i("RS : Games List = $games")

    Column {
        Column(
            modifier = modifier.padding(
                start = 24.dp,
                end = 24.dp
            ),
        ) {
            if(isLoading) ShowLoader("Loading Games...")
            ResultsText()
            if (games.isEmpty() && !isError)
                Centre(Modifier.fillMaxSize()) {
                    Text(
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        lineHeight = 34.sp,
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.empty_list)
                    )
                }
            if (!isError) {
                GameCardList(
                    games = games,
                    onClickGameDetails = onClickGameDetails,
                    onDeleteGame = { game: RugbyGameModel ->
                        resultsViewModel.deleteGames(game)
                    },
                )
            }
            if (isError) {
                ShowError(headline = error.message!! + " error...",
                    subtitle = error.toString(),
                    onClick = { resultsViewModel.getGames() })
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ResultsScreenPreview() {
    RugbyScoreTheme {
        PreviewResultsScreen( modifier = Modifier,
            games = fakeGames.toMutableStateList()
        )
    }
}

@Composable
fun PreviewResultsScreen(modifier: Modifier = Modifier,
                        games: SnapshotStateList<RugbyGameModel>
) {

    Column {
        Column(
            modifier = modifier.padding(
                start = 24.dp,
                end = 24.dp
            ),
        ) {
            ResultsText()
            if(games.isEmpty())
                Centre(Modifier.fillMaxSize()) {
                    Text(color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        lineHeight = 34.sp,
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.empty_list)
                    )
                }
            else
                GameCardList(
                    games = games,
                    onDeleteGame = {},
                    onClickGameDetails = { }
                )
        }
    }
}