package ie.setu.rugbygame.ui.components.game

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ie.setu.rugbygame.ui.theme.RugbyScoreTheme

@Composable
fun ScoreCounter(
    label: String,
    count: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(onClick = onDecrement) {
                Icon(Icons.Default.RemoveCircle, "Decrease", tint = MaterialTheme.colorScheme.primary)
            }
            Text(
                text = count.toString(),
                style = MaterialTheme.typography.headlineMedium
            )
            IconButton(onClick = onIncrement) {
                Icon(Icons.Default.AddCircle, "Increase", tint = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScoreCounterPreview() {
    RugbyScoreTheme {
        ScoreCounter(
            label = "Home",
            count = 1,
            onIncrement = {},
            onDecrement = {},
            Modifier
        )
    }
}