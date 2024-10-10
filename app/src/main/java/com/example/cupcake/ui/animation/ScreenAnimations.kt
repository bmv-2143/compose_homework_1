package com.example.cupcake.ui.animation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.navigation.NavBackStackEntry


private const val TWEEN_TIME_MS = 500

fun getScreenSlideOutTransition(
    isOrderCanceled: Boolean,
): AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition {
    return if (isOrderCanceled)
        slideOutTopToBottomFullHeight
    else
        slideOutLeftToRightFullWidth
}

fun getScreenSlideInTransition(
    isOrderCanceled: Boolean
): AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition {
    return if (isOrderCanceled)
        slideInTopToBottomFullHeight
    else
        slideInLeftToRightFullWidth
}

val slideInRightToLeftFullWidth:
        AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? =
    {
        slideInHorizontally(
            initialOffsetX = { fullWidth -> fullWidth },
            animationSpec = tween(TWEEN_TIME_MS)
        )
    }

val slideInLeftToRightFullWidth:
        AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition =
    {
        slideInHorizontally(
            initialOffsetX = { fullWidth -> -fullWidth },
            animationSpec = tween(TWEEN_TIME_MS)
        )
    }

val slideOutRightToLeftFullWidth:
        AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition =
    {
        slideOutHorizontally(
            targetOffsetX = { fullWidth -> -fullWidth },
            animationSpec = tween(TWEEN_TIME_MS)
        )
    }

val slideOutLeftToRightFullWidth:
        AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition =
    {
        slideOutHorizontally(
            targetOffsetX = { fullWidth -> fullWidth },
            animationSpec = tween(TWEEN_TIME_MS)
        )
    }

val slideOutTopToBottomFullHeight:
        AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition =
    {
        slideOutVertically(
            targetOffsetY = { fullHeight -> fullHeight },
            animationSpec = tween(TWEEN_TIME_MS)
        )
    }

val slideInTopToBottomFullHeight:
        AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition =
    {
        slideInVertically(
            initialOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(TWEEN_TIME_MS)
        )
    }