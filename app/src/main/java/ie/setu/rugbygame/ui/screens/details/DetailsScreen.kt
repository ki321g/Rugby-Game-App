package ie.setu.rugbygame.ui.screens.details

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ie.setu.rugbygame.data.model.RugbyGameModel
import ie.setu.rugbygame.data.model.fakeGames
import ie.setu.rugbygame.ui.components.details.DetailsScreenText
import ie.setu.rugbygame.ui.components.details.ReadOnlyTextField
import ie.setu.rugbygame.ui.components.game.ScoreBoard
import ie.setu.rugbygame.ui.components.game.ScoreCalculator
import ie.setu.rugbygame.ui.components.game.ScoreTypes
import ie.setu.rugbygame.ui.components.game.ScoreBoard
import ie.setu.rugbygame.ui.components.general.ShowLoader
import ie.setu.rugbygame.ui.theme.RugbyScoreTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    detailViewModel: DetailsViewModel = hiltViewModel()
) {
    val game = detailViewModel.game.value
    val errorEmptyMessage = "Message Cannot be Empty..."
    val errorShortMessage = "Message must be at least 2 characters"
    var text by rememberSaveable { mutableStateOf("") }
    // Home Details
    var homeTeam by rememberSaveable { mutableStateOf("") }
    var homeScore by rememberSaveable { mutableIntStateOf(0) }
    // Away Details
    var awayTeam by rememberSaveable { mutableStateOf("") }
    var awayScore by rememberSaveable { mutableIntStateOf(0) }

    var onMessageChanged by rememberSaveable { mutableStateOf(false) }
    var onHomeTeamChanged by rememberSaveable { mutableStateOf(false) }
    var onAwayTeamChanged by rememberSaveable { mutableStateOf(false) }
    var isEmptyError by rememberSaveable { mutableStateOf(false) }
    var isShortError by rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current
    val isError = detailViewModel.isErr.value
    val error = detailViewModel.error.value
    val isLoading = detailViewModel.isLoading.value

    if(isLoading) ShowLoader("Retrieving Game Details...")
    
    // Setting Game Values
    homeTeam = game.homeTeam
    awayTeam = game.awayTeam

    homeScore = ScoreCalculator.calculateTotalScore(
        tries = game.homeTries,
        conversions = game.homeConversions,
        penalties = game.homePenalties,
        dropGoals = game.homeDropGoals
    )

    awayScore = ScoreCalculator.calculateTotalScore(
        tries = game.awayTries,
        conversions = game.awayConversions,
        penalties = game.awayPenalties,
        dropGoals = game.awayDropGoals
    )

    fun validate(text: String, field: String) {
        isEmptyError = text.isEmpty()
        isShortError = text.length < 2

        if (field == "homeTeam") {
            onHomeTeamChanged = true
            onAwayTeamChanged = false
        } else if(field == "awayTeam") {
            onHomeTeamChanged = false
            onAwayTeamChanged = true
        }
        onMessageChanged = !(isEmptyError || isShortError)
    }

    if(isError)
        Toast.makeText(context,"Unable to fetch Details at this Time...",
            Toast.LENGTH_SHORT).show()
    if(!isError && !isLoading)
        Column(modifier = modifier.padding(
            start = 24.dp,
            end = 24.dp,
        ),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
//        DetailsScreenText()
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize().padding(
                    start = 10.dp,
                    end = 10.dp,
                ),
            )
            {
                //Date Game Started Field
                Spacer(modifier.height(height = 5.dp))
                ReadOnlyTextField(value = game.dateGame.toString(),
                    label = "Game Date")

                // Home Team Name Field
                OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                    value = homeTeam,
                    onValueChange = {
                        homeTeam = it
                        validate(homeTeam, "homeTeam")
                        game.homeTeam = homeTeam
                    },
                    maxLines = 2,
                    label = { Text(text = "Home Team") },
                    isError = isEmptyError && onHomeTeamChanged || isShortError && onHomeTeamChanged ,
                    trailingIcon = {
                        if (isEmptyError && onHomeTeamChanged || isShortError && onHomeTeamChanged)
                            Icon(Icons.Filled.Warning,"error", tint = MaterialTheme.colorScheme.error)
                        else
                            Icon(
                                Icons.Default.Edit, contentDescription = "add/edit",
                                tint = Color.Black
                            )
                    },
                    keyboardActions = KeyboardActions { validate(homeTeam, "homeTeam") },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                    )
                )

                // Display homeTeam Error Message
                if (isEmptyError && onHomeTeamChanged || isShortError && onHomeTeamChanged) {
                    Text(
                        text = if (isEmptyError && onHomeTeamChanged) errorEmptyMessage else errorShortMessage,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                //End of Home Team Name Field

                // Away Team Name Field
                OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                    value = awayTeam,
                    onValueChange = {
                        awayTeam = it
                        validate(awayTeam, "awayTeam")
                        game.awayTeam = awayTeam
                    },
                    maxLines = 2,
                    label = { Text(text = "Away Team") },
                    isError = isEmptyError && onAwayTeamChanged || isShortError && onAwayTeamChanged,
                    trailingIcon = {
                        if (isEmptyError && onAwayTeamChanged || isShortError && onAwayTeamChanged)
                            Icon(Icons.Filled.Warning,"error", tint = MaterialTheme.colorScheme.error)
                        else
                            Icon(
                                Icons.Default.Edit, contentDescription = "add/edit",
                                tint = Color.Black
                            )
                    },
                    keyboardActions = KeyboardActions { validate(awayTeam, "awayTeam") },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                    )
                )

                // Display awayTeam Error Message
                if (isEmptyError && onAwayTeamChanged || isShortError && onAwayTeamChanged) {
                    Text(
                        text = if (isEmptyError && onHomeTeamChanged) errorEmptyMessage else errorShortMessage,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                //End of Away Team Name Field

                //Start Score Board Details
                Spacer(modifier.height(height = 10.dp))
                ScoreBoard(
                    homeTeam = "Home",
                    awayTeam = "Away",
                    homeScore = homeScore,
                    awayScore = awayScore,
                    enabled = true,
                )

                //Start of Score Types
                Spacer(modifier.height(height = 10.dp))
                ScoreTypes (
                    modifier = modifier,
                    game = RugbyGameModel(
                        homeTeam = game.homeTeam,
                        homeTries = game.homeTries,
                        homeConversions = game.homeConversions,
                        homePenalties = game.homePenalties,
                        homeDropGoals = game.homeDropGoals,
                        awayTeam = game.awayTeam,
                        awayTries = game.awayTries,
                        awayConversions = game.awayConversions,
                        awayPenalties = game.awayPenalties,
                        awayDropGoals = game.awayDropGoals
                    ),
                    enabled = true,
                    onHomeTriesChange = { game.homeTries = it },
                    onHomeConversionsChange = { game.homeConversions = it },
                    onHomePenaltiesChange = { game.homePenalties = it },
                    onHomeDropGoalsChange = { game.homeDropGoals = it },
                    onAwayTriesChange = { game.awayTries = it },
                    onAwayConversionsChange = { game.awayConversions = it },
                    onAwayPenaltiesChange = { game.awayPenalties = it },
                    onAwayDropGoalsChange = { game.awayDropGoals = it },
                    onHomeScoreChange = { homeScore = it },
                    onAwayScoreChange = { awayScore = it }
                )

                //Start of Button
                Button(
                    onClick = {
                        detailViewModel.updateGame(game)
                        onMessageChanged = false
                    },
                    elevation = ButtonDefaults.buttonElevation(20.dp),
                    enabled = true //onMessageChanged
                ){
                    Icon(Icons.Default.Save, contentDescription = "Update")
                    Spacer(modifier.width(width = 8.dp))
                    Text(
                        text = "Update Game",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }
            }
        }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    RugbyScoreTheme {
        PreviewDetailScreen(modifier = Modifier,
            games = fakeGames.toMutableStateList()
        )
    }
}

@Composable
fun PreviewDetailScreen(modifier: Modifier,
                        games: SnapshotStateList<RugbyGameModel>) {

//    val game = RugbyGameModel()
    val errorEmptyMessage = "Message Cannot be Empty..."
    val errorShortMessage = "Message must be at least 2 characters"
    var text by rememberSaveable { mutableStateOf("") }
    // Home Details
    var homeTeam by rememberSaveable { mutableStateOf("") }
    var homeScore by rememberSaveable { mutableIntStateOf(0) }
    // Away Details
    var awayTeam by rememberSaveable { mutableStateOf("") }
    var awayScore by rememberSaveable { mutableIntStateOf(0) }

    var onMessageChanged by rememberSaveable { mutableStateOf(false) }
    var onHomeTeamChanged by rememberSaveable { mutableStateOf(false) }
    var onAwayTeamChanged by rememberSaveable { mutableStateOf(false) }
    var isEmptyError by rememberSaveable { mutableStateOf(false) }
    var isShortError by rememberSaveable { mutableStateOf(false) }

    // Setting Game Values
    homeTeam = games[1].homeTeam
    awayTeam = games[1].awayTeam

    homeScore = ScoreCalculator.calculateTotalScore(
        tries = games[1].homeTries,
        conversions = games[1].homeConversions,
        penalties = games[1].homePenalties,
        dropGoals = games[1].homeDropGoals
    )

    awayScore = ScoreCalculator.calculateTotalScore(
        tries = games[1].awayTries,
        conversions = games[1].awayConversions,
        penalties = games[1].awayPenalties,
        dropGoals = games[1].awayDropGoals
    )

    fun validate(text: String, field: String) {
        isEmptyError = text.isEmpty()
        isShortError = text.length < 2

        if (field == "homeTeam") {
            onHomeTeamChanged = true
            onAwayTeamChanged = false
        } else if(field == "awayTeam") {
            onHomeTeamChanged = false
            onAwayTeamChanged = true
        }
        onMessageChanged = !(isEmptyError || isShortError)
    }


    Column(
        modifier = modifier.padding(
            start = 10.dp,
            end = 10.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
//        DetailsScreenText()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 10.dp,
                    end = 0.dp,
                ),
        )
        {

            //Date Game Started Field
            Spacer(modifier.height(height = 5.dp))
            ReadOnlyTextField(value = games[1].dateGame.toString(),
                label = "Game Date")

            // Home Team Name Field
            OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                value = homeTeam,
                onValueChange = {
                    homeTeam = it
                    validate(homeTeam, "homeTeam")
                    games[1].homeTeam = homeTeam
                },
                maxLines = 2,
                label = { Text(text = "Home Team") },
                isError = isEmptyError && onHomeTeamChanged || isShortError && onHomeTeamChanged ,
                trailingIcon = {
                    if (isEmptyError && onHomeTeamChanged || isShortError && onHomeTeamChanged)
                        Icon(Icons.Filled.Warning,"error", tint = MaterialTheme.colorScheme.error)
                    else
                        Icon(
                            Icons.Default.Edit, contentDescription = "add/edit",
                            tint = Color.Black
                        )
                },
                keyboardActions = KeyboardActions { validate(homeTeam, "homeTeam") },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                )
            )

            // Display homeTeam Error Message
            if (isEmptyError && onHomeTeamChanged || isShortError && onHomeTeamChanged) {
                Text(
                    text = if (isEmptyError && onHomeTeamChanged) errorEmptyMessage else errorShortMessage,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            //End of Home Team Name Field

            // Away Team Name Field
            OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                value = awayTeam,
                onValueChange = {
                    awayTeam = it
                    validate(awayTeam, "awayTeam")
                    games[1].awayTeam = awayTeam
                },
                maxLines = 2,
                label = { Text(text = "Away Team") },
                isError = isEmptyError && onAwayTeamChanged || isShortError && onAwayTeamChanged,
                trailingIcon = {
                    if (isEmptyError && onAwayTeamChanged || isShortError && onAwayTeamChanged)
                        Icon(Icons.Filled.Warning,"error", tint = MaterialTheme.colorScheme.error)
                    else
                        Icon(
                            Icons.Default.Edit, contentDescription = "add/edit",
                            tint = Color.Black
                        )
                },
                keyboardActions = KeyboardActions { validate(awayTeam, "awayTeam") },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                )
            )

            // Display awayTeam Error Message
            if (isEmptyError && onAwayTeamChanged || isShortError && onAwayTeamChanged) {
                Text(
                    text = if (isEmptyError && onHomeTeamChanged) errorEmptyMessage else errorShortMessage,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            //End of Away Team Name Field

            //Start Score Board Details
            Spacer(modifier.height(height = 10.dp))
            ScoreBoard(
                homeTeam = "Home",
                awayTeam = "Away",
                homeScore = homeScore,
                awayScore = awayScore,
                enabled = true,
            )

            //Start of Score Types
            Spacer(modifier.height(height = 10.dp))
            ScoreTypes (
                modifier = modifier,
                game = RugbyGameModel(
                    homeTeam = games[1].homeTeam,
                    homeTries = games[1].homeTries,
                    homeConversions = games[1].homeConversions,
                    homePenalties = games[1].homePenalties,
                    homeDropGoals = games[1].homeDropGoals,
                    awayTeam = games[1].awayTeam,
                    awayTries = games[1].awayTries,
                    awayConversions = games[1].awayConversions,
                    awayPenalties = games[1].awayPenalties,
                    awayDropGoals = games[1].awayDropGoals
                ),
                enabled = true,
                onHomeTriesChange = { games[1].homeTries = it },
                onHomeConversionsChange = { games[1].homeConversions = it },
                onHomePenaltiesChange = { games[1].homePenalties = it },
                onHomeDropGoalsChange = { games[1].homeDropGoals = it },
                onAwayTriesChange = { games[1].awayTries = it },
                onAwayConversionsChange = { games[1].awayConversions = it },
                onAwayPenaltiesChange = { games[1].awayPenalties = it },
                onAwayDropGoalsChange = { games[1].awayDropGoals = it },
                onHomeScoreChange = { homeScore = it },
                onAwayScoreChange = { awayScore = it }
            )


            //Start of Button
            Button(
                onClick = {
                    onMessageChanged = false
                },
                elevation = ButtonDefaults.buttonElevation(20.dp),
                enabled = true
            ){
                Icon(Icons.Default.Save, contentDescription = "Update")
                Spacer(modifier.width(width = 8.dp))
                Text(
                    text = "Update Game",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
        }
    }
}

