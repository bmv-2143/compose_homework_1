package com.example.cupcake.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.cupcake.R
import com.example.cupcake.model.OrderViewModel
import com.example.cupcake.ui.theme.CupcakeTheme
import com.example.cupcake.ui.widgets.CupcakeTopBar
import com.example.cupcake.ui.widgets.RadioGroupOptionPicker


@Composable
fun PickupScreen(
    sharedViewModel: OrderViewModel,
    onNavigateUp: () -> Unit,
    onNavigateNext: () -> Unit,
    onNavigateToStart: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            CupcakeTopBar(
                title = stringResource(id = R.string.pickup_date),
                showUpArrow = true,
                onNavigateUp = onNavigateUp
            )
        }
    )
    { paddingValues ->
        Surface(
            modifier = modifier
                .padding(paddingValues)
                .padding(dimensionResource(id = R.dimen.side_margin))
                .verticalScroll(rememberScrollState())
        ) {
            PickupScreenContent(
                sharedViewModel = sharedViewModel,
                onNavigateNext = onNavigateNext,
                onNavigateToStart = onNavigateToStart,
            )
        }
    }
}

@Composable
private fun PickupScreenContent(
    sharedViewModel: OrderViewModel,
    onNavigateNext: () -> Unit,
    onNavigateToStart: () -> Unit,
    modifier: Modifier = Modifier
) {

    val dates = sharedViewModel.dateOptions
    val selectedOption: String by sharedViewModel.date.observeAsState(dates[0])
    val price by sharedViewModel.price.observeAsState("0.0")

    RadioGroupOptionPicker(
        options = dates,
        selectedOption = selectedOption,
        price = price,
        onOptionSelected = { sharedViewModel.setDate(it) },
        onConfirmSelection = onNavigateNext,
        onCancel = cancelOrder(sharedViewModel, onNavigateToStart),
        modifier = modifier
    )
}

@Preview(
    name = "Dark Mode With System UI",
    uiMode = Configuration.UI_MODE_NIGHT_UNDEFINED,
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun PickupScreenContentDarkWithSystemUi() {
    CupcakeTheme(darkTheme = true) {
        Surface {
            PickupScreenContent(
                sharedViewModel = OrderViewModel(),
                onNavigateNext = {},
                onNavigateToStart = {}
            )
        }
    }
}