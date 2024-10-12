package com.nadhifhayazee.taskmanager.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nadhifhayazee.taskmanager.ui.theme.AppTheme

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize()) {
        Text("home")
    }
}

@Preview
@Composable
private fun HomeScreenPrev() {
    AppTheme {
        HomeScreen()
    }
}