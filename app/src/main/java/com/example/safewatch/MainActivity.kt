
package com.example.safewatch


//import com.example.safewatch.presentation.ui.screens.incident_list.IncidentListScreen
//
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import com.example.safewatch.ui.theme.SafeWatchTheme

import androidx.activity.enableEdgeToEdge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.safewatch.presentation.ui.screens.incident_list.IncidentListScreen
import com.example.safewatch.ui.theme.SafeWatchTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SafeWatchTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    IncidentListScreen()
                }
            }
        }
    }
}