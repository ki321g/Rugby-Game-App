package ie.setu.rugbygame.ui.components.donate

import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ie.setu.rugbygame.R
import ie.setu.rugbygame.data.model.DonationModel
import ie.setu.rugbygame.data.model.fakeDonations
import ie.setu.rugbygame.ui.components.general.ShowLoader
import ie.setu.rugbygame.ui.screens.donate.DonateViewModel
import ie.setu.rugbygame.ui.screens.map.MapViewModel
import ie.setu.rugbygame.ui.screens.report.ReportViewModel
import ie.setu.rugbygame.ui.theme.RugbyScoreTheme
import timber.log.Timber

@Composable
fun DonateButton(
    modifier: Modifier = Modifier,
    donation: DonationModel,
    donateViewModel: DonateViewModel = hiltViewModel(),
    reportViewModel: ReportViewModel = hiltViewModel(),
    mapViewModel: MapViewModel = hiltViewModel(),
    onTotalDonatedChange: (Int) -> Unit
) {
    val donations = reportViewModel.uiDonations.collectAsState().value
    var totalDonated = donations.sumOf { it.paymentAmount }
    val context = LocalContext.current
    val message = stringResource(R.string.limitExceeded,donation.paymentAmount)

    val isError = donateViewModel.isErr.value
    val error = donateViewModel.error.value
    val locationLatLng = mapViewModel.currentLatLng.collectAsState().value

    LaunchedEffect(mapViewModel.currentLatLng){
        mapViewModel.getLocationUpdates()
    }

    Timber.i("DONATE BUTTON LAT/LNG COORDINATES " +
            "lat/Lng: " + "$locationLatLng ")

    Row {
        Button(
            onClick = {
                if(totalDonated + donation.paymentAmount <= 10000) {
                    totalDonated+=donation.paymentAmount
                    onTotalDonatedChange(totalDonated)
                    val donationLatLng = donation.copy(
                        latitude = locationLatLng.latitude,
                        longitude = locationLatLng.longitude
                    )
                    donateViewModel.insert(donationLatLng)
                }
                else
                    Toast.makeText(context,message,
                        Toast.LENGTH_SHORT).show()
            },
            elevation = ButtonDefaults.buttonElevation(20.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Donate")
            Spacer(modifier.width(width = 4.dp))
            Text(
                text = stringResource(R.string.donateButton),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
        }
        Spacer(modifier.weight(1f))
        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                ) {
                    append(stringResource(R.string.total) + " €")
                }


                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.secondary)
                ) {
                    append(totalDonated.toString())
                }
            })
    }

    Timber.i("DVM Button = : ${error.message}")
    //Required to refresh our 'totalDonated'
    if(isError)
        Toast.makeText(context,"Unable to Donate at this Time...",
            Toast.LENGTH_SHORT).show()
    else
        reportViewModel.getDonations()
}

@Preview(showBackground = true)
@Composable
fun DonateButtonPreview() {
    RugbyScoreTheme {
        PreviewDonateButton(
            Modifier,
            DonationModel(),
            donations = fakeDonations.toMutableStateList()
        ) {}
    }
}

@Composable
fun PreviewDonateButton(
    modifier: Modifier = Modifier,
    donation: DonationModel,
    donations: SnapshotStateList<DonationModel>,
    onTotalDonatedChange: (Int) -> Unit
) {

    var totalDonated = donations.sumOf { it.paymentAmount }
    val context = LocalContext.current
    val message = stringResource(R.string.limitExceeded,donation.paymentAmount)

    Row {
        Button(
            onClick = {
                if(totalDonated + donation.paymentAmount <= 10000) {
                    totalDonated+=donation.paymentAmount
                    onTotalDonatedChange(totalDonated)
                    donations.add(donation)
                    Timber.i("Donation info : $donation")
                    Timber.i("Donation List info : ${donations.toList()}")
                }
                else
                    Toast.makeText(context,message,
                        Toast.LENGTH_SHORT).show()
            },
            elevation = ButtonDefaults.buttonElevation(20.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Donate")
            Spacer(modifier.width(width = 4.dp))
            Text(
                text = stringResource(R.string.donateButton),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
        }
        Spacer(modifier.weight(1f))
        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                ) {
                    append(stringResource(R.string.total) + " €")
                }


                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.secondary)
                ) {
                    append(totalDonated.toString())
                }
            })
    }
}