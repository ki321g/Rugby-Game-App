package ie.setu.rugbygame.ui.screens.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import ie.setu.rugbygame.data.model.RugbyGameModel
import ie.setu.rugbygame.data.model.fakeGames
import ie.setu.rugbygame.ui.components.game.AmountPicker
import ie.setu.rugbygame.ui.components.game.DonateButton
import ie.setu.rugbygame.ui.components.game.MessageInput
import ie.setu.rugbygame.ui.components.game.ProgressBar
import ie.setu.rugbygame.ui.components.game.RadioButtonGroup
import ie.setu.rugbygame.ui.components.game.WelcomeText
import ie.setu.rugbygame.ui.screens.results.ResultsViewModel
import ie.setu.rugbygame.ui.theme.RugbyScoreTheme

@Composable
fun GameScreen(modifier: Modifier = Modifier,
                 resultsViewModel: ResultsViewModel = hiltViewModel()
) {
    var paymentType by remember { mutableStateOf("Paypal") }
    var paymentAmount by remember { mutableIntStateOf(10) }
    var paymentMessage by remember { mutableStateOf("Go Homer!") }
    var totalGamed by remember { mutableIntStateOf(0) }
    val games = resultsViewModel.uiGames.collectAsState().value

    totalGamed = games.sumOf { it.paymentAmount }

    Column {
        Column(
            modifier = modifier.padding(
                start = 24.dp,
                end = 24.dp
            ),
            verticalArrangement = Arrangement.spacedBy(30.dp),
        ) {
            WelcomeText()
            Row(
                verticalAlignment = Alignment.CenterVertically,
            )
            {
                RadioButtonGroup(
                    modifier = modifier,
                    onPaymentTypeChange = { paymentType = it }
                )
                Spacer(modifier.weight(1f))
                AmountPicker(
                    onPaymentAmountChange = { paymentAmount = it }
                )
            }
            ProgressBar(
                modifier = modifier,
                totalGamed = totalGamed)
            MessageInput(
                modifier = modifier,
                onMessageChange = { paymentMessage = it }
            )
            DonateButton (
                modifier = modifier,
                game = RugbyGameModel(paymentType = paymentType,
                    paymentAmount = paymentAmount,
                    message = paymentMessage),
                onTotalDonatedChange = { totalGamed = it }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    RugbyScoreTheme {
        PreviewGameScreen( modifier = Modifier,
            games = fakeGames.toMutableStateList())
    }
}

@Composable
fun PreviewGameScreen(modifier: Modifier = Modifier,
                        games: SnapshotStateList<RugbyGameModel>
) {
    var paymentType by remember { mutableStateOf("Paypal") }
    var paymentAmount by remember { mutableIntStateOf(10) }
    var paymentMessage by remember { mutableStateOf("Go Homer!") }
    var totalGamed by remember { mutableIntStateOf(0) }

    totalGamed = games.sumOf { it.paymentAmount }

    Column {
        Column(
            modifier = modifier.padding(
                start = 24.dp,
                end = 24.dp
            ),
            verticalArrangement = Arrangement.spacedBy(30.dp),
        ) {
            WelcomeText()
            Row(
                verticalAlignment = Alignment.CenterVertically,
            )
            {
                RadioButtonGroup(
                    modifier = modifier,
                    onPaymentTypeChange = { paymentType = it }
                )
                Spacer(modifier.weight(1f))
                AmountPicker(
                    onPaymentAmountChange = { paymentAmount = it }
                )
            }
            ProgressBar(
                modifier = modifier,
                totalGamed = totalGamed)
            MessageInput(
                modifier = modifier,
                onMessageChange = { paymentMessage = it }
            )
            DonateButton (
                modifier = modifier,
                game = RugbyGameModel(paymentType = paymentType,
                    paymentAmount = paymentAmount,
                    message = paymentMessage),
                onTotalDonatedChange = { totalGamed = it }
            )
        }
    }
}