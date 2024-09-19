package com.elfen.letsred

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.elfen.letsred.ui.Navigation
import com.elfen.letsred.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navHostController = rememberNavController()
            AppTheme  {
                Surface(color = AppTheme.colorScheme.background) {
                    Navigation(navHostController = navHostController)
                }
            }
        }
    }
}