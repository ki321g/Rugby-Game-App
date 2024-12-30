package ie.setu.rugbygame.ui.components.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.hilt.navigation.compose.hiltViewModel
import ie.setu.rugbygame.data.model.RugbyGameModel
import ie.setu.rugbygame.ui.screens.game.GameViewModel
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext

@Composable
fun ScoreTypes(
    modifier: Modifier = Modifier,
    game: RugbyGameModel,
    gameViewModel: GameViewModel = hiltViewModel(),
    enabled: Boolean = true,
    onHomeTriesChange: (Int) -> Unit,
    onHomeConversionsChange: (Int) -> Unit,
    onHomePenaltiesChange: (Int) -> Unit,
    onHomeDropGoalsChange: (Int) -> Unit,
    onAwayTriesChange: (Int) -> Unit,
    onAwayConversionsChange: (Int) -> Unit,
    onAwayPenaltiesChange: (Int) -> Unit,
    onAwayDropGoalsChange: (Int) -> Unit,
    onHomeScoreChange: (Int) -> Unit,
    onAwayScoreChange: (Int) -> Unit
) {
    val context = LocalContext.current
    var homeTries = game.homeTries
    var homeConversions = game.homeConversions
    var homePenalties = game.homePenalties
    var homeDropGoals = game.homeDropGoals
    var awayTries = game.awayTries
    var awayConversions = game.awayConversions
    var awayPenalties = game.awayPenalties
    var awayDropGoals = game.awayDropGoals
    var homeScore = ScoreCalculator.calculateTotalScore(
        tries = game.homeTries,
        conversions = game.homeConversions,
        penalties = game.homePenalties,
        dropGoals = game.homeDropGoals
    )
    var awayScore = ScoreCalculator.calculateTotalScore(
        tries = game.awayTries,
        conversions = game.awayConversions,
        penalties = game.awayPenalties,
        dropGoals = game.awayDropGoals
    )

    Column(
        modifier = Modifier.alpha(if (enabled) 1f else 0.5f)
    ) {
        // Start of Score Types
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Home Team Scoring
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                ScoreCounter(
                    label = "Tries (5)",
                    count = homeTries,
                    onIncrement = {
                        homeTries++
                        onHomeTriesChange(homeTries)
                        homeScore = ScoreCalculator.calculateTotalScore(
                            tries = homeTries,
                            conversions = homeConversions,
                            penalties = homePenalties,
                            dropGoals = homeDropGoals
                        )
                        onHomeScoreChange(homeScore)
//                        Toast.makeText(context,homeTries.toString(),Toast.LENGTH_SHORT).show()
                    },
                    onDecrement = {
                        if (homeTries > 0) {
                            homeTries--
                            onHomeTriesChange(homeTries)
                            homeScore = ScoreCalculator.calculateTotalScore(
                                tries = homeTries,
                                conversions = homeConversions,
                                penalties = homePenalties,
                                dropGoals = homeDropGoals
                            )
                            onHomeScoreChange(homeScore)
//                            Toast.makeText(context,homeTries.toString(),Toast.LENGTH_SHORT).show()
                        }
                    }
                )
                ScoreCounter(
                    label = "Conversions (2)",
                    count =  homeConversions,
                    onIncrement = {
                        homeConversions++
                        onHomeConversionsChange(homeConversions)
                        homeScore = ScoreCalculator.calculateTotalScore(
                            tries = homeTries,
                            conversions = homeConversions,
                            penalties = homePenalties,
                            dropGoals = homeDropGoals
                        )
                        onHomeScoreChange(homeScore)
                    },
                    onDecrement = {
                        if (homeConversions > 0) {
                            homeConversions--
                            onHomeConversionsChange(homeConversions)
                            homeScore = ScoreCalculator.calculateTotalScore(
                                tries = homeTries,
                                conversions = homeConversions,
                                penalties = homePenalties,
                                dropGoals = homeDropGoals
                            )
                            onHomeScoreChange(homeScore)
                        }
                    }
                )
                ScoreCounter(
                    label = "Penalties (3)",
                    count =  homePenalties,
                    onIncrement = {
                        homePenalties++
                        onHomePenaltiesChange(homePenalties)
                        homeScore = ScoreCalculator.calculateTotalScore(
                            tries = homeTries,
                            conversions = homeConversions,
                            penalties = homePenalties,
                            dropGoals = homeDropGoals
                        )
                        onHomeScoreChange(homeScore)
                    },
                    onDecrement = {
                        if (homePenalties > 0) {
                            homePenalties--
                            onHomePenaltiesChange(homePenalties)
                            homeScore = ScoreCalculator.calculateTotalScore(
                                tries = homeTries,
                                conversions = homeConversions,
                                penalties = homePenalties,
                                dropGoals = homeDropGoals
                            )
                            onHomeScoreChange(homeScore)
                        }
                    }
                )
                ScoreCounter(
                    label = "Drop Goals (3)",
                    count =  homeDropGoals,
                    onIncrement = {
                        homeDropGoals++
                        onHomeDropGoalsChange(homeDropGoals)
                        homeScore = ScoreCalculator.calculateTotalScore(
                            tries = homeTries,
                            conversions = homeConversions,
                            penalties = homePenalties,
                            dropGoals = homeDropGoals
                        )
                        onHomeScoreChange(homeScore)
                    },
                    onDecrement = {
                        if (homeDropGoals > 0) {
                            homeDropGoals--
                            onHomeDropGoalsChange(homeDropGoals)
                            homeScore = ScoreCalculator.calculateTotalScore(
                                tries = homeTries,
                                conversions = homeConversions,
                                penalties = homePenalties,
                                dropGoals = homeDropGoals
                            )
                            onHomeScoreChange(homeScore)
                        }
                    }
                )
            }

            // Away Team Scoring
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                ScoreCounter(
                    label = "Tries (5)",
                    count = awayTries,
                    onIncrement = {
                        awayTries++
                        onAwayTriesChange(awayTries)
                        awayScore = ScoreCalculator.calculateTotalScore(
                            tries = awayTries,
                            conversions = awayConversions,
                            penalties = awayPenalties,
                            dropGoals = awayDropGoals
                        )
                        onHomeScoreChange(homeScore)
                    },
                    onDecrement = {
                        if (awayTries > 0) {
                            awayTries--
                            onAwayTriesChange(awayTries)
                            awayScore = ScoreCalculator.calculateTotalScore(
                                tries = awayTries,
                                conversions = awayConversions,
                                penalties = awayPenalties,
                                dropGoals = awayDropGoals
                            )
                            onAwayScoreChange(awayScore)
                        } }
                )
                ScoreCounter(
                    label = "Conversions (2)",
                    count =  awayConversions,
                    onIncrement = {
                        awayConversions++
                        onAwayConversionsChange(awayConversions)
                        awayScore = ScoreCalculator.calculateTotalScore(
                            tries = awayTries,
                            conversions = awayConversions,
                            penalties = awayPenalties,
                            dropGoals = awayDropGoals
                        )
                        onAwayScoreChange(awayScore)
                    },
                    onDecrement = {
                        if (awayConversions > 0) {
                            awayConversions--
                            onAwayConversionsChange(awayConversions)
                            awayScore = ScoreCalculator.calculateTotalScore(
                                tries = awayTries,
                                conversions = awayConversions,
                                penalties = awayPenalties,
                                dropGoals = awayDropGoals
                            )
                            onAwayScoreChange(awayScore)
                        }
                    }
                )
                ScoreCounter(
                    label = "Penalties (3)",
                    count =  awayPenalties,
                    onIncrement = {
                        awayPenalties++
                        onAwayPenaltiesChange(awayPenalties)
                        awayScore = ScoreCalculator.calculateTotalScore(
                            tries = awayTries,
                            conversions = awayConversions,
                            penalties = awayPenalties,
                            dropGoals = awayDropGoals
                        )
                        onAwayScoreChange(awayScore)
                    },
                    onDecrement = {
                        if (awayPenalties > 0) {
                            awayPenalties--
                            onAwayPenaltiesChange(awayPenalties)
                            awayScore = ScoreCalculator.calculateTotalScore(
                                tries = awayTries,
                                conversions = awayConversions,
                                penalties = awayPenalties,
                                dropGoals = awayDropGoals
                            )
                            onAwayScoreChange(awayScore)
                        }
                    }
                )
                ScoreCounter(
                    label = "Drop Goals (3)",
                    count =  awayDropGoals,
                    onIncrement = {
                        awayDropGoals++
                        onAwayDropGoalsChange(awayDropGoals)
                        awayScore = ScoreCalculator.calculateTotalScore(
                            tries = awayTries,
                            conversions = awayConversions,
                            penalties = awayPenalties,
                            dropGoals = awayDropGoals
                        )
                        onAwayScoreChange(awayScore)
                    },
                    onDecrement = {
                        if (awayDropGoals > 0) {
                            awayDropGoals--
                            onAwayDropGoalsChange(awayDropGoals)
                            awayScore = ScoreCalculator.calculateTotalScore(
                                tries = awayTries,
                                conversions = awayConversions,
                                penalties = awayPenalties,
                                dropGoals = awayDropGoals
                            )
                            onAwayScoreChange(awayScore)
                        }
                    }
                )
            }
        }
    }
}