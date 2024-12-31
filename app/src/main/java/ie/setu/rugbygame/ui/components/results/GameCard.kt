package ie.setu.rugbygame.ui.components.results

import android.net.Uri
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ie.setu.rugbygame.R
import ie.setu.rugbygame.ui.components.game.ScoreCalculator
import ie.setu.rugbygame.ui.components.results.GameCard
import ie.setu.rugbygame.ui.theme.RugbyScoreTheme
import ie.setu.rugbygame.ui.theme.endGradientColor
import ie.setu.rugbygame.ui.theme.startGradientColor
import java.text.DateFormat
import java.util.Date

@Composable
fun GameCard(
    homeTeam: String,
    homeTeamTries: Int,
    homeTeamConversions: Int,
    homeTeamPenalties: Int,
    homeTeamDropGoals: Int,
    awayTeam: String,
    awayTeamTries: Int,
    awayTeamConversions: Int,
    awayTeamPenalties: Int,
    awayTeamDropGoals: Int,
    message: String,
    dateCreated: String,
    dateModified: String,
    onClickDelete: () -> Unit,
    onClickGameDetails: () -> Unit,
    photoUri: Uri
) {
    Card(
        border = BorderStroke(1.dp, Color.Black),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.padding(vertical = 2.dp, horizontal = 2.dp)
    ) {
        GameCardContent(homeTeam,
            homeTeamTries,
            homeTeamConversions,
            homeTeamPenalties,
            homeTeamDropGoals,
            awayTeam,
            awayTeamTries,
            awayTeamConversions,
            awayTeamPenalties,
            awayTeamDropGoals,
            message,
            dateCreated,
            dateModified,
            onClickDelete,
            onClickGameDetails,
            photoUri
        )
    }
}

@Composable
private fun GameCardContent(
    homeTeam:  String,
    homeTeamTries: Int,
    homeTeamConversions: Int,
    homeTeamPenalties: Int,
    homeTeamDropGoals: Int,
    awayTeam: String,
    awayTeamTries: Int,
    awayTeamConversions: Int,
    awayTeamPenalties: Int,
    awayTeamDropGoals: Int,
    message: String,
    dateCreated: String,
    dateModified: String,
    onClickDelete: () -> Unit,
    onClickGameDetails: () -> Unit,
    photoUri: Uri
) {
    var expanded by remember { mutableStateOf(false) }
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }

    val homeScore = ScoreCalculator.calculateTotalScore(
        tries = homeTeamTries,
        conversions = homeTeamConversions,
        penalties = homeTeamPenalties,
        dropGoals = homeTeamDropGoals
    )

    val awayScore = ScoreCalculator.calculateTotalScore(
        tries = awayTeamTries,
        conversions = awayTeamConversions,
        penalties = awayTeamPenalties,
        dropGoals = awayTeamDropGoals
    )

    Row(
        modifier = Modifier
            .padding(2.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            .background(brush = Brush.horizontalGradient(
                colors = listOf(
                    startGradientColor,
                    endGradientColor,
                )
            ))
    ) {
        // Card Content

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(14.dp)
        ) {
            // Game Date
            Text(
                text = "Game Date: $dateCreated",
                style = MaterialTheme.typography.labelSmall
            )

            // Home Team Section
            Text(
                text = "Home Team",
                style = MaterialTheme.typography.labelSmall
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = homeTeam,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
            }

            // Score Row with Photo
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(photoUri)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = "$homeScore",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                Text(text = "vs")
                Text(
                    text = "$awayScore",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
            }

            // Away Team Section
            Text(
                text = "Away Team",
                style = MaterialTheme.typography.labelSmall
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = awayTeam,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
            }

//            Text(text = "Date: $dateCreated", style = MaterialTheme.typography.labelSmall)

            if (expanded) {
                // Score Details Table
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("")
                    Text("Home")
                    Text("Away")
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Tries")
                    Text("$homeTeamTries")
                    Text("$awayTeamTries")
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Conversions")
                    Text("$homeTeamConversions")
                    Text("$awayTeamConversions")
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Penalties")
                    Text("$homeTeamPenalties")
                    Text("$awayTeamPenalties")
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Drop Goals")
                    Text("$homeTeamDropGoals")
                    Text("$awayTeamDropGoals")
                }

                Text(text = "Modified: $dateModified", style = MaterialTheme.typography.labelSmall)

                // Existing buttons row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    FilledTonalButton(onClick = onClickGameDetails) {
                        Text(text = "Show More")
                    }

                    FilledTonalIconButton(onClick = {
                        showDeleteConfirmDialog = true
                    }) {
                        Icon(Icons.Filled.Delete, "Delete Game")
                    }
                }

                if (showDeleteConfirmDialog) {
                    showDeleteAlert(
                        onDismiss = { showDeleteConfirmDialog = false },
                        onDelete = onClickDelete,
                    )
                }

                if (showDeleteConfirmDialog) {
                    showDeleteAlert(
                        onDismiss = { showDeleteConfirmDialog = false },
                        onDelete = onClickDelete,
                    )
                }
            }
        }


        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess
                else Icons.Filled.ExpandMore,
                contentDescription = if (expanded) {
                    stringResource(R.string.show_less)
                } else {
                    stringResource(R.string.show_more)
                }
            )
        }
    }
}

@Composable
fun showDeleteAlert(
    onDismiss: () -> Unit,
    onDelete: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss ,
        title = { Text(stringResource(id = R.string.confirm_delete)) },
        text = { Text(stringResource(id = R.string.confirm_delete_message)) },
        confirmButton = {
            Button(
                onClick = {
                    onDelete()
                    //onRefresh()
                }
            ) { Text("Yes") }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text("No") }
        }
    )
}

@Preview
@Composable
fun GameCardPreview() {
    RugbyScoreTheme {
        GameCard(
            homeTeam = "HomeTeam",
            homeTeamTries = 2,
            homeTeamConversions = 1,
            homeTeamPenalties = 3,
            homeTeamDropGoals = 2,
            awayTeam = "AwayTeam",
            awayTeamTries = 2,
            awayTeamConversions = 1,
            awayTeamPenalties = 3,
            awayTeamDropGoals = 2,
            message = """
                A message entered 
                by the user..."
            """.trimIndent(),
            dateCreated = DateFormat.getDateTimeInstance().format(Date()),
            dateModified = DateFormat.getDateTimeInstance().format(Date()),
            onClickDelete = { },
            onClickGameDetails = {},
            photoUri = Uri.EMPTY
        )
    }
}
