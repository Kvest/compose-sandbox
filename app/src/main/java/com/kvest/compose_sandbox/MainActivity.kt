package com.kvest.compose_sandbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.kvest.compose_sandbox.ui.navigation.ComposeSandboxNavHost
import com.kvest.compose_sandbox.ui.theme.ComposesandboxTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposesandboxTheme {
                val navController = rememberNavController()

                ComposeSandboxNavHost(navController = navController)
            }
        }
    }
}