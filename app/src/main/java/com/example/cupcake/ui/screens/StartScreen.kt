package com.example.cupcake.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.cupcake.R
import com.example.cupcake.model.OrderViewModel
import com.example.cupcake.ui.theme.CupcakeTheme
import com.example.cupcake.ui.widgets.CupcakeTopBar
import com.example.cupcake.ui.widgets.RectangularFilledButton

@Composable
fun StartScreen(sharedViewModel: OrderViewModel, onNavigateToFlavorScreen: () -> Unit, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            CupcakeTopBar(stringResource(id = R.string.app_name))
        }
    )
    { paddingValues ->

        LaunchedEffect(Unit) {
            sharedViewModel.resetOrderCanceled()
        }

        Surface(modifier = modifier
            .padding(paddingValues).padding(dimensionResource(id = R.dimen.side_margin))
            .verticalScroll(rememberScrollState())) {
            StartScreenContent(sharedViewModel, onNavigateToFlavorScreen, modifier)
        }
    }
}

@Composable
private fun StartScreenContent(
    sharedViewModel: OrderViewModel, onNavigateToFlavorScreen: () -> Unit, modifier: Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CupcakeImage()
        OrderCupcakesText()
        OrderCupcakeButtons(sharedViewModel, onNavigateToFlavorScreen)
    }
}

@Composable
private fun OrderCupcakeButtons(sharedViewModel: OrderViewModel, onNavigateToFlavorScreen: () -> Unit) {
    val defaultFlavor = stringResource(R.string.vanilla)
    val cupcakeQuantities by remember {
        mutableStateOf(
            listOf(
                R.string.one_cupcake to 1,
                R.string.six_cupcakes to 6,
                R.string.twelve_cupcakes to 12
            )
        )
    }

    cupcakeQuantities.forEach { (stringRes, quantity) ->
        val buttonText = stringResource(stringRes)
        RectangularFilledButton(
            buttonText = buttonText,
            onClick = {
                sharedViewModel.orderCupcake(quantity, defaultFlavor)
                onNavigateToFlavorScreen()
            },
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.margin_between_elements))
        )
    }
}

@Composable
private fun CupcakeImage(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.cupcake),
        contentDescription = "Cupcake",
        contentScale = ContentScale.Inside,
        modifier = modifier
            .width(dimensionResource(R.dimen.image_size))
            .height(dimensionResource(R.dimen.image_size))
            .padding(dimensionResource(id = R.dimen.margin_between_elements))
    )
}

@Composable
private fun OrderCupcakesText(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(id = R.string.order_cupcakes),
        fontFamily = FontFamily.SansSerif,
        style = MaterialTheme.typography.bodyMedium,
        fontSize = 34.sp,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
        modifier = modifier.padding(bottom = dimensionResource(id = R.dimen.side_margin)),
    )
}

@Preview(showBackground = true)
@Composable
private fun StartScreenLightPreview() {
    CupcakeTheme(darkTheme = false) {
        StartScreen(sharedViewModel = OrderViewModel(), onNavigateToFlavorScreen = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun StartScreenDarkPreview() {
    CupcakeTheme(darkTheme = true) {
        StartScreen(sharedViewModel = OrderViewModel(), onNavigateToFlavorScreen = {})
    }
}