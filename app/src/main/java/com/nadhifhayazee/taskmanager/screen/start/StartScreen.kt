package com.nadhifhayazee.taskmanager.screen.start

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.nadhifhayazee.taskmanager.screen.ScreenHome
import com.nadhifhayazee.taskmanager.screen.ScreenLogin
import kotlinx.coroutines.delay

@Composable
fun StartScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: StartScreenViewModel = hiltViewModel()
) {
    val userId by viewModel.userId.collectAsState()

    LaunchedEffect(userId) {
        delay(1000)
        if (userId == null) {
            navController.navigate(ScreenLogin) {
                popUpTo(0)
            }
        } else {
            navController.navigate(ScreenHome) {
                popUpTo(0)
            }
        }
    }
}