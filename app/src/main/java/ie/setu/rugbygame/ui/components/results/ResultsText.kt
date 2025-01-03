package ie.setu.rugbygame.ui.components.results

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ie.setu.rugbygame.R
import ie.setu.rugbygame.ui.components.results.ResultsText
import ie.setu.rugbygame.ui.theme.RugbyScoreTheme

@Composable
fun ResultsText(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(
            top = 12.dp,
            bottom = 12.dp
        ),
        verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = stringResource(R.string.resultsTitle),
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            color = Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ReportPreview() {
    RugbyScoreTheme {
        ResultsText()
    }
}