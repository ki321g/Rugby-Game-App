package ie.setu.rugbygame.ui.components.game

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ie.setu.rugbygame.R
import ie.setu.rugbygame.ui.theme.RugbyScoreTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun AwayTeamNameInput(
    modifier: Modifier = Modifier,
    onAwayTeamNameChange: (String) -> Unit
) {

    var awayTeam by remember { mutableStateOf("") }

    OutlinedTextField(
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
        ),
        maxLines = 2,
        value = awayTeam,
        onValueChange = {
            awayTeam = it
            onAwayTeamNameChange(awayTeam)
        },
        modifier = modifier.fillMaxWidth(),
        label = {
            Text(stringResource(R.string.away_team))
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AwayTeamNamePreview() {
    RugbyScoreTheme {
        AwayTeamNameInput(
            Modifier,
            onAwayTeamNameChange = {})
    }
}