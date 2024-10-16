package com.example.cupcake.ui.screens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.cupcake.R
import com.example.cupcake.model.OrderViewModel
import com.example.cupcake.ui.theme.CupcakeTheme
import com.example.cupcake.ui.widgets.CupcakeDivider
import com.example.cupcake.ui.widgets.CupcakeTopBar
import com.example.cupcake.ui.widgets.RectangularFilledButton

@Composable
fun SummaryScreen(
    sharedViewModel: OrderViewModel,
    onNavigateUp: () -> Unit,
    onNavigateToStart: () -> Unit,
    modifier: Modifier = Modifier
) {

    ScreenTransitionInProgressFinishedEffect(sharedViewModel)
    BackPressedHandlerWithDisablingClicks(sharedViewModel)

    Scaffold(
        topBar = {
            CupcakeTopBar(
                title = stringResource(id = R.string.choose_flavor),
                showUpArrow = true,
                onNavigateUp = onNavigateUp,
                enabled = isUiEnabled(sharedViewModel)
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
            SummaryScreenContent(
                sharedViewModel = sharedViewModel,
                onNavigateToStart = onNavigateToStart,
            )
        }
    }
}

@Composable
private fun SummaryScreenContent(
    sharedViewModel: OrderViewModel,
    onNavigateToStart: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(dimensionResource(R.dimen.side_margin))) {

        OrderResult(
            name = stringResource(R.string.quantity),
            value = sharedViewModel.quantity.value.toString(),
        )

        OrderResult(
            name = stringResource(R.string.flavor),
            value = sharedViewModel.flavor.value.toString(),
        )

        OrderResult(
            name = stringResource(R.string.pickup_date),
            value = sharedViewModel.date.value.toString(),
        )

        Text(
            text = stringResource(id = R.string.total_price, sharedViewModel.price.value.toString()).uppercase(),
            fontSize = 22.sp,
            modifier = Modifier
                .padding(top = dimensionResource(R.dimen.side_margin))
                .align(alignment = Alignment.End)
        )

        SendOrderButton(sharedViewModel)
        CancelOrderButton(sharedViewModel, onNavigateToStart)
    }
}

@Composable
private fun OrderResult(name: String, value: String) {
    Text(text = name.uppercase(), fontSize = 18.sp)
    Text(
        text = value,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = dimensionResource(R.dimen.order_summary_margin))
    )
    CupcakeDivider()
}

@Composable
private fun SendOrderButton(sharedViewModel: OrderViewModel) {
    val context = LocalContext.current
    RectangularFilledButton(
        buttonText = stringResource(R.string.send).uppercase(),
        onClick = { sendOrder(context, sharedViewModel) },
        modifier = Modifier
            .padding(top = dimensionResource(R.dimen.side_margin))
            .fillMaxWidth(),
        enabled = isUiEnabled(sharedViewModel)
    )
}

/**
 * Submit the order by sharing out the order details to another app via an implicit intent.
 */
private fun sendOrder(context: Context, sharedViewModel: OrderViewModel) {
    val orderSummary = makeOrderSummary(sharedViewModel, context)
    val intent = makeImplicitSendOrderIntent(context, orderSummary)

    if (context.packageManager.resolveActivity(intent, 0) != null) {
        context.startActivity(intent)
    }
}

private fun makeImplicitSendOrderIntent(context: Context, orderSummary: String) = Intent(Intent.ACTION_SEND)
    .setType("text/plain")
    .putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.new_cupcake_order))
    .putExtra(Intent.EXTRA_TEXT, orderSummary)

private fun makeOrderSummary(
    sharedViewModel: OrderViewModel,
    context: Context
): String {
    val numberOfCupcakes = sharedViewModel.quantity.value ?: 0
    val orderSummary = context.getString(
        R.string.order_details,
        context.resources.getQuantityString(R.plurals.cupcakes, numberOfCupcakes, numberOfCupcakes),
        sharedViewModel.flavor.value.toString(),
        sharedViewModel.date.value.toString(),
        sharedViewModel.price.value.toString()
    )
    return orderSummary
}

@Composable
private fun CancelOrderButton(sharedViewModel: OrderViewModel, onNavigateToStart: () -> Unit) {
    OutlinedButton(
        onClick = cancelOrder(sharedViewModel, onNavigateToStart),
        shape = RectangleShape,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(R.dimen.margin_between_elements)),
        enabled = isUiEnabled(sharedViewModel)
    ) {
        Text(
            text = stringResource(R.string.cancel).uppercase(),
        )
    }
}

@Preview
@Composable
private fun SummaryScreenPreview() {
    CupcakeTheme {
        SummaryScreen(
            sharedViewModel = OrderViewModel(),
            onNavigateUp = { },
            onNavigateToStart = { }
        )
    }
}

