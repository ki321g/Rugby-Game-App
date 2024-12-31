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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import ie.setu.rugbygame.ui.components.details.DetailsScreenText
import ie.setu.rugbygame.ui.components.details.ReadOnlyTextField
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
    var homeTeamName by rememberSaveable { mutableStateOf("") }
    var awayTeamName by rememberSaveable { mutableStateOf("") }
    var onMessageChanged by rememberSaveable { mutableStateOf(false) }
    var onHomeTeamChanged by rememberSaveable { mutableStateOf(false) }
    var onAwayTeamChanged by rememberSaveable { mutableStateOf(false) }
    var isEmptyError by rememberSaveable { mutableStateOf(false) }
    var isShortError by rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current
    val isError = detailViewModel.isErr.value
    val error = detailViewModel.error.value
    val isLoading = detailViewModel.isLoading.value

    if(isLoading) ShowLoader("Retrieving Donation Details...")

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
                //Date Donated Field
                ReadOnlyTextField(value = game.dateGame.toString(),
                    label = "Game Date")
                // Home Team Name Field
                homeTeamName = game.homeTeam
                OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                    value = homeTeamName,
                    onValueChange = {
                        homeTeamName = it
                        validate(homeTeamName, "homeTeam")
                        game.homeTeam = homeTeamName
                    },
                    maxLines = 2,
                    label = { Text(text = "Home Team") },
                    isError = isEmptyError && onHomeTeamChanged || isShortError && onHomeTeamChanged ,
                    supportingText = {
                        if (isEmptyError && onHomeTeamChanged) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = errorEmptyMessage,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                        else
                            if (isShortError && onHomeTeamChanged) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = errorShortMessage,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                    },
                    trailingIcon = {
                        if (isEmptyError && onHomeTeamChanged || isShortError && onHomeTeamChanged)
                            Icon(Icons.Filled.Warning,"error", tint = MaterialTheme.colorScheme.error)
                        else
                            Icon(
                                Icons.Default.Edit, contentDescription = "add/edit",
                                tint = Color.Black
                            )
                    },
                    keyboardActions = KeyboardActions { validate(homeTeamName, "homeTeam") },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                    )
                )
                //End of Home Team Name Field
                // Away Team Name Field
                awayTeamName = game.awayTeam
                OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                    value = awayTeamName,
                    onValueChange = {
                        awayTeamName = it
                        validate(awayTeamName, "awayTeam")
                        game.awayTeam = awayTeamName
                    },
                    maxLines = 2,
                    label = { Text(text = "Away Team") },
                    isError = isEmptyError && onAwayTeamChanged || isShortError && onAwayTeamChanged,
                    supportingText = {
                        if (isEmptyError && onAwayTeamChanged) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = errorEmptyMessage,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                        else
                            if (isShortError && onAwayTeamChanged) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = errorShortMessage,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                    },
                    trailingIcon = {
                        if (isEmptyError && onAwayTeamChanged || isShortError && onAwayTeamChanged)
                            Icon(Icons.Filled.Warning,"error", tint = MaterialTheme.colorScheme.error)
                        else
                            Icon(
                                Icons.Default.Edit, contentDescription = "add/edit",
                                tint = Color.Black
                            )
                    },
                    keyboardActions = KeyboardActions { validate(awayTeamName, "awayTeam") },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                    )
                )
                //End of Away Team Name Field
                Spacer(modifier.height(height = 48.dp))
                Button(
                    onClick = {
                        detailViewModel.updateGame(game)
                        onMessageChanged = false
                    },
                    elevation = ButtonDefaults.buttonElevation(20.dp),
                    enabled = onMessageChanged
                ){
                    Icon(Icons.Default.Save, contentDescription = "Save")
                    Spacer(modifier.width(width = 8.dp))
                    Text(
                        text = "Save",
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
        PreviewDetailScreen(modifier = Modifier)
    }
}

@Composable
fun PreviewDetailScreen(modifier: Modifier) {

    val game = RugbyGameModel()
    val errorEmptyMessage = "Message Cannot be Empty..."
    val errorShortMessage = "Message must be at least 2 characters"
    var text by rememberSaveable { mutableStateOf("") }
    var homeTeamName by rememberSaveable { mutableStateOf("") }
    var awayTeamName by rememberSaveable { mutableStateOf("") }
    var onMessageChanged by rememberSaveable { mutableStateOf(false) }
    var isEmptyError by rememberSaveable { mutableStateOf(false) }
    var isShortError by rememberSaveable { mutableStateOf(false) }

    fun validate(text: String) {
        isEmptyError = text.isEmpty()
        isShortError = text.length < 2
        onMessageChanged = true
    }

    Column(
        modifier = modifier.padding(
            start = 10.dp,
            end = 10.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
//        DetailsScreenText()
        //           Row (modifier = Modifier.fillMaxSize()) {
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
            //Date Donated Field
            ReadOnlyTextField(value = game.dateGame.toString(),
                label = "Game Date")
            // Home Team Name Field
            homeTeamName = game.homeTeam
            OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                value = homeTeamName,
                onValueChange = {
                    homeTeamName = it
                    validate(homeTeamName)
                    game.homeTeam = homeTeamName
                },
                maxLines = 2,
                label = { Text(text = "Home, Team") },
                isError = isEmptyError || isShortError,
                supportingText = {
                    if (isEmptyError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = errorEmptyMessage,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    else
                        if (isShortError) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = errorShortMessage,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                },
                trailingIcon = {
                    if (isEmptyError || isShortError)
                        Icon(Icons.Filled.Warning,"error", tint = MaterialTheme.colorScheme.error)
                    else
                        Icon(
                            Icons.Default.Edit, contentDescription = "add/edit",
                            tint = Color.Black
                        )
                },
                keyboardActions = KeyboardActions { validate(homeTeamName) },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                )
            )
            //End of Home Team Name Field
            // Away Team Name Field
            awayTeamName = game.awayTeam
            OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                value = awayTeamName,
                onValueChange = {
                    awayTeamName = it
                    validate(awayTeamName)
                    game.awayTeam = awayTeamName
                },
                maxLines = 2,
                label = { Text(text = "Away Team") },
                isError = isEmptyError || isShortError,
                supportingText = {
                    if (isEmptyError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = errorEmptyMessage,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    else
                        if (isShortError) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = errorShortMessage,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                },
                trailingIcon = {
                    if (isEmptyError || isShortError)
                        Icon(Icons.Filled.Warning,"error", tint = MaterialTheme.colorScheme.error)
                    else
                        Icon(
                            Icons.Default.Edit, contentDescription = "add/edit",
                            tint = Color.Black
                        )
                },
                keyboardActions = KeyboardActions { validate(awayTeamName) },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                )
            )
            //End of Away Team Name Field
            //            //Payment Type Field
//            OutlinedTextField(modifier = modifier.fillMaxWidth(),
//                value = game.homeTeam,
//                onValueChange = { },
//                label = { Text(text = "Home Team") },
//                readOnly = false,
//                colors = OutlinedTextFieldDefaults.colors(
//                    unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
//                )
//            )
//            //Payment Amount Field
//            OutlinedTextField(modifier = modifier.fillMaxWidth(),
//                value = game.awayTeam,
//                onValueChange = { },
//                label = { Text(text = "Away Team") },
//                readOnly = false,
//                colors = OutlinedTextFieldDefaults.colors(
//                    unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
//                )
//            )
//            //Date Donated Field
//            OutlinedTextField(modifier = modifier.fillMaxWidth(),
//                value = game.dateDonated.toString(),
//                onValueChange = { },
//                label = { Text(text = "Date Donated") },
//                readOnly = true,
//                colors = OutlinedTextFieldDefaults.colors(
//                    unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
//                )
//            )
//            text = game.message
//            OutlinedTextField(modifier = Modifier.fillMaxWidth(),
//                value = text,
//                onValueChange = {
//                    text = it
//                    validate(text)
//                    game.message = text
//                },
//                maxLines = 2,
//                label = { Text(text = "Message") },
//                isError = isEmptyError || isShortError,
//                supportingText = {
//                    if (isEmptyError) {
//                        Text(
//                            modifier = Modifier.fillMaxWidth(),
//                            text = errorEmptyMessage,
//                            color = MaterialTheme.colorScheme.error
//                        )
//                    }
//                    else
//                        if (isShortError) {
//                            Text(
//                                modifier = Modifier.fillMaxWidth(),
//                                text = errorShortMessage,
//                                color = MaterialTheme.colorScheme.error
//                            )
//                        }
//                },
//                trailingIcon = {
//                    if (isEmptyError || isShortError)
//                        Icon(Icons.Filled.Warning,"error", tint = MaterialTheme.colorScheme.error)
//                    else
//                        Icon(
//                            Icons.Default.Edit, contentDescription = "add/edit",
//                            tint = Color.Black
//                        )
//                },
//                keyboardActions = KeyboardActions { validate(text) },
//                colors = OutlinedTextFieldDefaults.colors(
//                    unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
//                )
//            )
            Spacer(modifier.height(height = 48.dp))
            Button(
                onClick = {
                    onMessageChanged = false
                },
                elevation = ButtonDefaults.buttonElevation(20.dp),
                enabled = onMessageChanged
            ){
                Icon(Icons.Default.Save, contentDescription = "Save")
                Spacer(modifier.width(width = 8.dp))
                Text(
                    text = "Save",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
        }
    }
}

