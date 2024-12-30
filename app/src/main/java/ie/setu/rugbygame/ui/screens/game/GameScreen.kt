package ie.setu.rugbygame.ui.screens.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import ie.setu.rugbygame.data.model.RugbyGameModel
import ie.setu.rugbygame.data.model.fakeGames
import ie.setu.rugbygame.ui.components.game.AwayTeamNameInput
import ie.setu.rugbygame.ui.components.game.HomeTeamNameInput
import ie.setu.rugbygame.ui.components.game.StartButton
import ie.setu.rugbygame.ui.components.game.ScoreBoard
import ie.setu.rugbygame.ui.components.game.ScoreCalculator
import ie.setu.rugbygame.ui.components.game.ScoreCounter
import ie.setu.rugbygame.ui.components.game.ScoreTypes
import ie.setu.rugbygame.ui.components.game.WelcomeText
import ie.setu.rugbygame.ui.screens.results.ResultsViewModel
import ie.setu.rugbygame.ui.theme.RugbyScoreTheme



@Composable
fun GameScreen(modifier: Modifier = Modifier,
               gameViewModel: GameViewModel = hiltViewModel(),
               resultsViewModel: ResultsViewModel = hiltViewModel()
) {
    var isGameStarted by remember { mutableStateOf(false) }

    // Home Details
    var homeTeam by remember { mutableStateOf("HomeTeam") }
    var homeTries by remember { mutableIntStateOf(0) }
    var homeConversions by remember { mutableIntStateOf(0) }
    var homePenalties by remember { mutableIntStateOf(0) }
    var homeDropGoals by remember { mutableIntStateOf(0) }
    var homeScore by remember { mutableIntStateOf(0) }
    // Away Details
    var awayTeam by remember { mutableStateOf("AwayTeam") }
    var awayTries by remember { mutableIntStateOf(0) }
    var awayConversions by remember { mutableIntStateOf(0) }
    var awayPenalties by remember { mutableIntStateOf(0) }
    var awayDropGoals by remember { mutableIntStateOf(0) }
    var awayScore by remember { mutableIntStateOf(0) }

    val games = resultsViewModel.uiGames.collectAsState().value

    homeScore = ScoreCalculator.calculateTotalScore(
        tries = homeTries,
        conversions = homeConversions,
        penalties = homePenalties,
        dropGoals = homeDropGoals
    )

    awayScore = ScoreCalculator.calculateTotalScore(
        tries = awayTries,
        conversions = awayConversions,
        penalties = awayPenalties,
        dropGoals = awayDropGoals
    )

    Column {
        Column(
            modifier = modifier.padding(
                start = 24.dp,
                end = 24.dp
            ),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            // Welcome Text
//            WelcomeText()
            
            // Home Team Details
            HomeTeamNameInput(
                modifier = modifier,
                onHomeTeamNameChange = { homeTeam = it }
            )

//            Spacer(modifier = Modifier.height(16.dp))

            // Away Team Details
            AwayTeamNameInput(
                modifier = modifier,
                onAwayTeamNameChange = { awayTeam = it }
            )

//            Spacer(modifier = Modifier.height(32.dp))

            // Score Board Details
            ScoreBoard(
                homeTeam = "Home",
                awayTeam = "Away",
                homeScore = homeScore,
                awayScore = awayScore,
//                enabled = isGameStarted,
                enabled = true,
            )

            Spacer(modifier = Modifier.height(32.dp))

            ScoreTypes (
                modifier = modifier,
                game = RugbyGameModel(
                    homeTeam = homeTeam,
                    homeTries = homeTries,
                    homeConversions = homeConversions,
                    homePenalties = homePenalties,
                    homeDropGoals = homeDropGoals,
                    awayTeam = awayTeam,
                    awayTries = awayTries,
                    awayConversions = awayConversions,
                    awayPenalties = awayPenalties,
                    awayDropGoals = awayDropGoals
                ),
//                enabled = isGameStarted,
                enabled = true,
                onHomeTriesChange = { homeTries = it },
                onHomeConversionsChange = { homeConversions = it },
                onHomePenaltiesChange = { homePenalties = it },
                onHomeDropGoalsChange = { homeDropGoals = it },
                onAwayTriesChange = { awayTries = it },
                onAwayConversionsChange = { awayConversions = it },
                onAwayPenaltiesChange = { awayPenalties = it },
                onAwayDropGoalsChange = { awayDropGoals = it },
                onHomeScoreChange = { homeScore = it },
                onAwayScoreChange = { awayScore = it }
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                StartButton(
                    modifier = modifier,
                    game = RugbyGameModel(
                        homeTeam = homeTeam,
                        homeTries = homeTries,
                        homeConversions = homeConversions,
                        homePenalties = homePenalties,
                        homeDropGoals = homeDropGoals,
                        awayTeam = awayTeam,
                        awayTries = awayTries,
                        awayConversions = awayConversions,
                        awayPenalties = awayPenalties,
                        awayDropGoals = awayDropGoals
                    ),
                    onIsGameStartedChange = { isGameStarted = it },
//                    onClickGameDetails = onClickGameDetails
                )
            }
        }
    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun GameScreenPreview() {
//    RugbyScoreTheme {
//        PreviewGameScreen( modifier = Modifier,
//            games = fakeGames.toMutableStateList())
//    }
//}
//
//@Composable
//fun PreviewGameScreen(modifier: Modifier = Modifier,
//                      onClickGameDetails: (String) -> Unit,
//                        games: SnapshotStateList<RugbyGameModel>
//) {
//    var isGameStarted by remember { mutableStateOf(false) }
//
//    // Home Details
//    var homeTeam by remember { mutableStateOf("HomeTeam") }
//    var homeTries by remember { mutableIntStateOf(0) }
//    var homeConversions by remember { mutableIntStateOf(0) }
//    var homePenalties by remember { mutableIntStateOf(0) }
//    var homeDropGoals by remember { mutableIntStateOf(0) }
//    var homeScore by remember { mutableIntStateOf(0) }
//    // Away Details
//    var awayTeam by remember { mutableStateOf("AwayTeam") }
//    var awayTries by remember { mutableIntStateOf(0) }
//    var awayConversions by remember { mutableIntStateOf(0) }
//    var awayPenalties by remember { mutableIntStateOf(0) }
//    var awayDropGoals by remember { mutableIntStateOf(0) }
//    var awayScore by remember { mutableIntStateOf(0) }
//
//    homeScore = ScoreCalculator.calculateTotalScore(
//        tries = homeTries,
//        conversions = homeConversions,
//        penalties = homePenalties,
//        dropGoals = homeDropGoals
//    )
//
//    awayScore = ScoreCalculator.calculateTotalScore(
//        tries = awayTries,
//        conversions = awayConversions,
//        penalties = awayPenalties,
//        dropGoals = awayDropGoals
//    )
//
//    Column {
//        Column(
//            modifier = modifier.padding(
//                start = 24.dp,
//                end = 24.dp
//            ),
//            verticalArrangement = Arrangement.spacedBy(30.dp),
//        ) {
//            // Welcome Text
//            WelcomeText()
//
//            // Home Team Details
//            HomeTeamNameInput(
//                modifier = modifier,
//                onHomeTeamNameChange = { homeTeam = it }
//            )
//            // Away Team Details
//            AwayTeamNameInput(
//                modifier = modifier,
//                onAwayTeamNameChange = { awayTeam = it }
//            )
//
//            // Score Board Details
//            ScoreBoard(
//                homeTeam = "Home",
//                awayTeam = "Away",
//                homeScore = homeScore,
//                awayScore = awayScore,
//                enabled = isGameStarted
//            )
//
//            Spacer(modifier = Modifier.height(32.dp))
//
//            ScoreTypes (
//                modifier = modifier,
//                game = RugbyGameModel(
//                    homeTeam = homeTeam,
//                    homeTries = homeTries,
//                    homeConversions = homeConversions,
//                    homePenalties = homePenalties,
//                    homeDropGoals = homeDropGoals,
//                    awayTeam = awayTeam,
//                    awayTries = awayTries,
//                    awayConversions = awayConversions,
//                    awayPenalties = awayPenalties,
//                    awayDropGoals = awayDropGoals
//                ),
//                enabled = isGameStarted,
//                onHomeScoreChange = { homeScore = it },
//                onAwayScoreChange = { awayScore = it }
//            )
//
//            StartButton (
//                modifier = modifier,
//                game = RugbyGameModel(
//                    homeTeam = homeTeam,
//                    homeTries = homeTries,
//                    homeConversions = homeConversions,
//                    homePenalties = homePenalties,
//                    homeDropGoals = homeDropGoals,
//                    awayTeam = awayTeam,
//                    awayTries = awayTries,
//                    awayConversions = awayConversions,
//                    awayPenalties = awayPenalties,
//                    awayDropGoals = awayDropGoals
//                ),
//                onIsGameStartedChange = { isGameStarted = it },
//                onClickGameDetails = onClickGameDetails
//            )
//
//        }
//    }
//}
