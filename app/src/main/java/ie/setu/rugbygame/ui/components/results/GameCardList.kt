package ie.setu.rugbygame.ui.components.results

import android.net.Uri
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import ie.setu.rugbygame.data.model.RugbyGameModel
import ie.setu.rugbygame.data.model.fakeGames
import ie.setu.rugbygame.ui.components.results.GameCard
import ie.setu.rugbygame.ui.components.results.GameCardList
import ie.setu.rugbygame.ui.theme.RugbyScoreTheme
import java.text.DateFormat


@Composable
internal fun GameCardList(
    games: List<RugbyGameModel>,
    modifier: Modifier = Modifier,
    onDeleteGame: (RugbyGameModel) -> Unit,
    onClickGameDetails: (String) -> Unit
) {
    LazyColumn {
        items(
            items = games,
            key = { game -> game._id }
        ) { game ->
            GameCard(
                homeTeam = game.homeTeam,
                homeTeamTries = game.homeTries,
                homeTeamConversions = game.homeConversions,
                homeTeamPenalties = game.homePenalties,
                homeTeamDropGoals = game.homeDropGoals,
                awayTeam = game.awayTeam,
                awayTeamTries = game.awayTries,
                awayTeamConversions = game.awayConversions,
                awayTeamPenalties = game.awayPenalties,
                awayTeamDropGoals = game.awayDropGoals,
                message = "hello",
                dateCreated = DateFormat.getDateTimeInstance().format(game.dateGame),
                dateModified = DateFormat.getDateTimeInstance().format(game.dateModified),
                onClickDelete = { onDeleteGame(game) },
                onClickGameDetails = { onClickGameDetails(game._id) },
                photoUri = Uri.parse(game.imageUri)
            )
        }
    }

}

@Preview(showBackground = true,
    wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE
)

@Composable
fun GameCardListPreview() {
    RugbyScoreTheme {
        GameCardList(
            fakeGames.toMutableStateList(),
            onDeleteGame = {}
        ) { }
    }
}