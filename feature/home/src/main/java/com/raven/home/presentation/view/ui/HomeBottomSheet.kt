package com.raven.home.presentation.view.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.raven.home.R
import com.raven.home.presentation.view.state.HomeState
import com.raven.home.presentation.viewmodel.HomeViewModel

@Composable
fun HomeBottomSheet(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    onPeriodSelected: (Int) -> Unit,
    onClose: () -> Unit
) {
    val uiState: HomeState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_top_bottom_sheet),
            contentDescription = "top bottom sheet"
        )
        IconButton(modifier = Modifier.align(Alignment.End), onClick = { onClose() }) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Filled.Close,
                contentDescription = "close bottom sheet",
                tint = Color.Unspecified
            )
        }
        Text(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 12.dp, bottom = 8.dp),
            text = stringResource(id = R.string.label_most_viewed),
            style = MaterialTheme.typography.body1
        )
        RadioButtonItem(
            label = stringResource(id = R.string.radio_button_last_day),
            period = 1,
            periodSelected = uiState.periodSelected,
            onPeriodSelected = onPeriodSelected
        )
        RadioButtonItem(
            label = stringResource(id = R.string.radio_button_last_7_days),
            period = 7,
            periodSelected = uiState.periodSelected,
            onPeriodSelected = onPeriodSelected
        )
        RadioButtonItem(
            label = stringResource(id = R.string.radio_button_last_30_days),
            period = 30,
            periodSelected = uiState.periodSelected,
            onPeriodSelected = onPeriodSelected
        )
    }
}

@Composable
fun RadioButtonItem(
    label: String,
    period: Int,
    periodSelected: Int,
    onPeriodSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = (period == periodSelected),
                onClick = { onPeriodSelected(period) }
            )
            .padding(start = 8.dp, top = 4.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = (period == periodSelected),
            onClick = { onPeriodSelected(period) },
            colors = RadioButtonDefaults.colors(selectedColor = Color.Gray)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.body1
        )
    }
}
