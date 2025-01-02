package ie.setu.rugbygame.ui.components.game

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import ie.setu.rugbygame.ui.theme.RugbyScoreTheme

@Composable
fun ScoreBoard(
    homeTeam: String,
    awayTeam: String,
    homeScore: Int,
    awayScore: Int,
    enabled: Boolean = true,
) {
    Column(
        modifier = Modifier.alpha(if (enabled) 1f else 0.5f)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = homeTeam, style = MaterialTheme.typography.headlineMedium)
                Text(text = homeScore.toString(), style = MaterialTheme.typography.displayLarge)
            }
            Text(text = "vs", style = MaterialTheme.typography.headlineMedium)
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = awayTeam, style = MaterialTheme.typography.headlineMedium)
                Text(text = awayScore.toString(), style = MaterialTheme.typography.displayLarge)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScoreBoardPreview() {
    RugbyScoreTheme {
        ScoreBoard(
            homeTeam = "Home",
            awayTeam = "Away",
            homeScore = 15,
            awayScore = 21
        )
    }
}