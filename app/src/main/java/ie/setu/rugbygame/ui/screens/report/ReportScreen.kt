package ie.setu.rugbygame.ui.screens.report

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ie.setu.rugbygame.R
import ie.setu.rugbygame.data.model.DonationModel
import ie.setu.rugbygame.data.model.fakeDonations
import ie.setu.rugbygame.ui.components.general.Centre
import ie.setu.rugbygame.ui.components.general.ShowError
import ie.setu.rugbygame.ui.components.general.ShowLoader
import ie.setu.rugbygame.ui.components.general.ShowRefreshList
import ie.setu.rugbygame.ui.components.report.DonationCardList
import ie.setu.rugbygame.ui.components.report.ReportText
import ie.setu.rugbygame.ui.theme.RugbyScoreTheme
import timber.log.Timber


@Composable
fun ReportScreen(modifier: Modifier = Modifier,
                 onClickDonationDetails: (String) -> Unit,
                 reportViewModel: ReportViewModel = hiltViewModel()) {

    val donations = reportViewModel.uiDonations.collectAsState().value
    val isError = reportViewModel.iserror.value
    val error = reportViewModel.error.value
    val isLoading = reportViewModel.isloading.value

    Timber.i("RS : Donations List = $donations")

//    LaunchedEffect(Unit) {
//        reportViewModel.getDonations()
//    }

    Column {
        Column(
            modifier = modifier.padding(
                start = 24.dp,
                end = 24.dp
            ),
        ) {
            if(isLoading) ShowLoader("Loading Donations...")
            ReportText()
//            if(!isError)
//                ShowRefreshList(onClick = { reportViewModel.getDonations() })
            if (donations.isEmpty() && !isError)
                Centre(Modifier.fillMaxSize()) {
                    Text(
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        lineHeight = 34.sp,
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.empty_list)
                    )
                }
            if (!isError) {
                DonationCardList(
                    donations = donations,
                    onClickDonationDetails = onClickDonationDetails,
                    onDeleteDonation = { donation: DonationModel ->
                        reportViewModel.deleteDonation(donation)
                    },
//                    onRefreshList = { reportViewModel.getDonations() }
                )
            }
            if (isError) {
                ShowError(headline = error.message!! + " error...",
                    subtitle = error.toString(),
                    onClick = { reportViewModel.getDonations() })
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ReportScreenPreview() {
    RugbyScoreTheme {
        PreviewReportScreen( modifier = Modifier,
            donations = fakeDonations.toMutableStateList()
        )
    }
}

@Composable
fun PreviewReportScreen(modifier: Modifier = Modifier,
                        donations: SnapshotStateList<DonationModel>
) {

    Column {
        Column(
            modifier = modifier.padding(
                start = 24.dp,
                end = 24.dp
            ),
        ) {
            ReportText()
            if(donations.isEmpty())
                Centre(Modifier.fillMaxSize()) {
                    Text(color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        lineHeight = 34.sp,
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.empty_list)
                    )
                }
            else
                DonationCardList(
                    donations = donations,
                    onDeleteDonation = {},
                    onClickDonationDetails = { }
                )
        }
    }
}