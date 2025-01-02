package ie.setu.rugbygame.ui.components.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ie.setu.rugbygame.R
import ie.setu.rugbygame.ui.screens.home.HomeViewModel
import ie.setu.rugbygame.ui.theme.RugbyScoreTheme

@Composable
fun WelcomeText(modifier: Modifier = Modifier,
        homeViewModel: HomeViewModel = hiltViewModel()) {

    val currentUser = homeViewModel.currentUser
    val isActiveSession = homeViewModel.isAuthenticated()
    val userName = if (isActiveSession) currentUser?.displayName else ""
    val currentUserName = userName!!
    val welcomeText = stringResource(R.string.gameTitle) + " " + currentUserName + "!"
    Column(
        modifier = modifier.padding(
            top = 12.dp,
            bottom = 12.dp
        ),
        verticalArrangement = Arrangement.spacedBy(24.dp)) {
        Text(
            text = welcomeText,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            color = Color.Black
        )
        Text(
            text = stringResource(R.string.gameSubtitle),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomePreview() {
    RugbyScoreTheme {
        WelcomeText()
    }
}