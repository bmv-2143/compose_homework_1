package com.example.cupcake.ui.screens

import com.example.cupcake.model.OrderViewModel

internal fun cancelOrder(
    sharedViewModel: OrderViewModel,
    onNavigateToStart: () -> Unit
): () -> Unit = {
    sharedViewModel.resetOrder()
    onNavigateToStart()
}