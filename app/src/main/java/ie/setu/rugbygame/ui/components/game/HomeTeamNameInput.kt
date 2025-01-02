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
fun HomeTeamNameInput(
    modifier: Modifier = Modifier,
    onHomeTeamNameChange: (String) -> Unit
) {

    var homeTeam by remember { mutableStateOf("") }

    OutlinedTextField(
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
        ),
        maxLines = 2,
        value = homeTeam,
        onValueChange = {
            homeTeam = it
            onHomeTeamNameChange(homeTeam)
        },
        modifier = modifier.fillMaxWidth(),
        label = {
            Text(stringResource(R.string.home_team))
        }
    )
}

@Preview(showBackground = true)
@Composable
fun HomeTeamNamePreview() {
    RugbyScoreTheme {
        HomeTeamNameInput(
            Modifier,
            onHomeTeamNameChange = {})
    }
}