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
    onHomeScoreChange: (Int) -> Unit,
    onAwayScoreChange: (Int) -> Unit
) {
    val context = LocalContext.current
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
                    count = game.homeTries,
                    onIncrement = {
                        game.homeTries++
                        homeScore = ScoreCalculator.calculateTotalScore(
                            tries = game.homeTries,
                            conversions = game.homeConversions,
                            penalties = game.homePenalties,
                            dropGoals = game.homeDropGoals
                        )
                        onHomeScoreChange(homeScore)
                        Toast.makeText(context,game.homeTries.toString(),Toast.LENGTH_SHORT).show()
                    },
                    onDecrement = {
                        if (game.homeTries > 0) {
                            game.homeTries--
                            homeScore = ScoreCalculator.calculateTotalScore(
                                tries = game.homeTries,
                                conversions = game.homeConversions,
                                penalties = game.homePenalties,
                                dropGoals = game.homeDropGoals
                            )
                            onHomeScoreChange(homeScore)
                            Toast.makeText(context,game.homeTries.toString(),Toast.LENGTH_SHORT).show()
                        }
                    }
                )
                ScoreCounter(
                    label = "Conversions (2)",
                    count =  game.homeConversions,
                    onIncrement = {
                        game.homeConversions++
                        homeScore = ScoreCalculator.calculateTotalScore(
                            tries = game.homeTries,
                            conversions = game.homeConversions,
                            penalties = game.homePenalties,
                            dropGoals = game.homeDropGoals
                        )
                        onHomeScoreChange(homeScore)
                    },
                    onDecrement = {
                        if (game.homeConversions > 0) {
                            game.homeConversions--
                            homeScore = ScoreCalculator.calculateTotalScore(
                                tries = game.homeTries,
                                conversions = game.homeConversions,
                                penalties = game.homePenalties,
                                dropGoals = game.homeDropGoals
                            )
                            onHomeScoreChange(homeScore)
                        }
                    }
                )
                ScoreCounter(
                    label = "Penalties (3)",
                    count =  game.homePenalties,
                    onIncrement = {
                        game.homePenalties++
                        homeScore = ScoreCalculator.calculateTotalScore(
                            tries = game.homeTries,
                            conversions = game.homeConversions,
                            penalties = game.homePenalties,
                            dropGoals = game.homeDropGoals
                        )
                        onHomeScoreChange(homeScore)
                    },
                    onDecrement = {
                        if (game.homePenalties > 0) {
                            game.homePenalties--
                            homeScore = ScoreCalculator.calculateTotalScore(
                                tries = game.homeTries,
                                conversions = game.homeConversions,
                                penalties = game.homePenalties,
                                dropGoals = game.homeDropGoals
                            )
                            onHomeScoreChange(homeScore)
                        }
                    }
                )
                ScoreCounter(
                    label = "Drop Goals (3)",
                    count =  game.homeDropGoals,
                    onIncrement = {
                        game.homeDropGoals++
                        homeScore = ScoreCalculator.calculateTotalScore(
                            tries = game.homeTries,
                            conversions = game.homeConversions,
                            penalties = game.homePenalties,
                            dropGoals = game.homeDropGoals
                        )
                        onHomeScoreChange(homeScore)
                    },
                    onDecrement = {
                        if (game.homeDropGoals > 0) {
                            game.homeDropGoals--
                            homeScore = ScoreCalculator.calculateTotalScore(
                                tries = game.homeTries,
                                conversions = game.homeConversions,
                                penalties = game.homePenalties,
                                dropGoals = game.homeDropGoals
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
                    count = game.awayTries,
                    onIncrement = { game.awayTries++ },
                    onDecrement = { if (game.awayTries > 0) game.awayTries-- }
                )
                ScoreCounter(
                    label = "Conversions (2)",
                    count =  game.awayConversions,
                    onIncrement = {
                        game.awayConversions++
                        awayScore = ScoreCalculator.calculateTotalScore(
                            tries = game.awayTries,
                            conversions = game.awayConversions,
                            penalties = game.awayPenalties,
                            dropGoals = game.awayDropGoals
                        )
                        onAwayScoreChange(awayScore)
                    },
                    onDecrement = {
                        if (game.awayConversions > 0) {
                            game.awayConversions--
                            awayScore = ScoreCalculator.calculateTotalScore(
                                tries = game.awayTries,
                                conversions = game.awayConversions,
                                penalties = game.awayPenalties,
                                dropGoals = game.awayDropGoals
                            )
                            onAwayScoreChange(awayScore)
                        }
                    }
                )
                ScoreCounter(
                    label = "Penalties (3)",
                    count =  game.awayPenalties,
                    onIncrement = {
                        game.awayPenalties++
                        awayScore = ScoreCalculator.calculateTotalScore(
                            tries = game.awayTries,
                            conversions = game.awayConversions,
                            penalties = game.awayPenalties,
                            dropGoals = game.awayDropGoals
                        )
                        onAwayScoreChange(awayScore)
                    },
                    onDecrement = {
                        if (game.awayPenalties > 0) {
                            game.awayPenalties--
                            awayScore = ScoreCalculator.calculateTotalScore(
                                tries = game.awayTries,
                                conversions = game.awayConversions,
                                penalties = game.awayPenalties,
                                dropGoals = game.awayDropGoals
                            )
                            onAwayScoreChange(awayScore)
                        }
                    }
                )
                ScoreCounter(
                    label = "Drop Goals (3)",
                    count =  game.awayDropGoals,
                    onIncrement = {
                        game.awayDropGoals++
                        awayScore = ScoreCalculator.calculateTotalScore(
                            tries = game.awayTries,
                            conversions = game.awayConversions,
                            penalties = game.awayPenalties,
                            dropGoals = game.awayDropGoals
                        )
                        onAwayScoreChange(awayScore)
                    },
                    onDecrement = {
                        if (game.awayDropGoals > 0) {
                            game.awayDropGoals--
                            awayScore = ScoreCalculator.calculateTotalScore(
                                tries = game.awayTries,
                                conversions = game.awayConversions,
                                penalties = game.awayPenalties,
                                dropGoals = game.awayDropGoals
                            )
                            onAwayScoreChange(awayScore)
                        }
                    }
                )
            }
        }
    }
}