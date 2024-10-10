package com.example.cupcake.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cupcake.model.OrderViewModel
import com.example.cupcake.ui.animation.getScreenSlideInTransition
import com.example.cupcake.ui.animation.getScreenSlideOutTransition
import com.example.cupcake.ui.animation.slideInLeftToRightFullWidth
import com.example.cupcake.ui.animation.slideInRightToLeftFullWidth
import com.example.cupcake.ui.animation.slideOutRightToLeftFullWidth
import com.example.cupcake.ui.screens.FlavorScreen
import com.example.cupcake.ui.screens.PickupScreen
import com.example.cupcake.ui.screens.StartScreen
import com.example.cupcake.ui.screens.SummaryScreen
import kotlinx.serialization.Serializable


@Serializable
object StartDestination

@Serializable
object FlavorDestination

@Serializable
object PickupDestination

@Serializable
object SummaryDestination


@Composable
fun Navigation(sharedViewModel: OrderViewModel, modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = StartDestination) {
        composable<StartDestination>(
            enterTransition = slideInRightToLeftFullWidth,
            exitTransition = slideOutRightToLeftFullWidth,
            popEnterTransition = {
                getScreenSlideInTransition(sharedViewModel.isOrderCanceled)()
            },
        ) { _ ->
            StartScreen(
                sharedViewModel,
                onNavigateToFlavorScreen = navigateToFlavorScreen(navController),
                modifier = modifier
            )
        }
        composable<FlavorDestination>(
            enterTransition = slideInRightToLeftFullWidth,
            exitTransition = slideOutRightToLeftFullWidth,
            popEnterTransition = slideInLeftToRightFullWidth,
            popExitTransition = {
                Log.e("Navigation", "FlavorDestination popExitTransition")
                getScreenSlideOutTransition(sharedViewModel.isOrderCanceled)()
            },
        ) { _ ->
            FlavorScreen(
                sharedViewModel = sharedViewModel,
                onNavigateUp = navigateUp(navController),
                onNavigateNext = navigateToPickupScreen(navController),
                onNavigateToStart = navigateToStartScreen(navController),
                modifier = modifier
            )
        }

        composable<PickupDestination>(
            enterTransition = slideInRightToLeftFullWidth,
            exitTransition = slideOutRightToLeftFullWidth,
            popEnterTransition = slideInLeftToRightFullWidth,
            popExitTransition = {
                getScreenSlideOutTransition(sharedViewModel.isOrderCanceled)()
            },
        ) { _ ->
            PickupScreen(
                sharedViewModel = sharedViewModel,
                onNavigateUp = navigateUp(navController),
                onNavigateNext = navigateToSummaryScreen(navController),
                onNavigateToStart = navigateToStartScreen(navController),
                modifier = modifier
            )
        }

        composable<SummaryDestination>(
            enterTransition = slideInRightToLeftFullWidth,
            exitTransition = slideOutRightToLeftFullWidth,
            popEnterTransition = slideInLeftToRightFullWidth,
            popExitTransition = {
                getScreenSlideOutTransition(sharedViewModel.isOrderCanceled)()
            },
        ) { _ ->
            SummaryScreen(
                sharedViewModel,
                onNavigateUp = navigateUp(navController),
                onNavigateToStart = navigateToStartScreen(navController),
                modifier = modifier
            )
        }
    }
}

private fun navigateToFlavorScreen(navController: NavHostController): () -> Unit = {
    navController.navigate(FlavorDestination)
}

private fun navigateToStartScreen(navController: NavHostController): () -> Unit = {
    navController.popBackStack(StartDestination, inclusive = false)
}

private fun navigateToPickupScreen(navController: NavHostController): () -> Unit = {
    navController.navigate(PickupDestination)
}

private fun navigateToSummaryScreen(navController: NavHostController): () -> Unit = {
    navController.navigate(SummaryDestination)
}

private fun navigateUp(navController: NavHostController): () -> Unit = {
    navController.navigateUp()
}