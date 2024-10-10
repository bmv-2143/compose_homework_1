package com.example.cupcake

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.cupcake.model.OrderViewModel
import com.example.cupcake.navigation.Navigation
import com.example.cupcake.ui.theme.CupcakeTheme

class MainComposeActivity : ComponentActivity() {

    private val sharedViewModel: OrderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CupcakeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Navigation(sharedViewModel, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
