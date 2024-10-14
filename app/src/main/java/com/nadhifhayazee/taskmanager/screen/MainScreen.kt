package com.nadhifhayazee.taskmanager.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nadhifhayazee.taskmanager.screen.createTask.CreateTaskScreen
import com.nadhifhayazee.taskmanager.screen.gallery.GalleryScreen
import com.nadhifhayazee.taskmanager.screen.home.HomeScreen
import com.nadhifhayazee.taskmanager.screen.login.LoginScreen
import com.nadhifhayazee.taskmanager.screen.register.RegisterScreen
import com.nadhifhayazee.taskmanager.screen.start.StartScreen
import com.nadhifhayazee.taskmanager.screen.task.TaskScreen
import com.nadhifhayazee.taskmanager.ui.theme.AppTheme
import kotlinx.serialization.Serializable

@Composable
fun MainScreen(modifier: Modifier = Modifier, viewModel: MainViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val selectedIndex by viewModel.selectedMenuIndex

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            bottomBar = {
                val backstackRoute =
                    navController.currentBackStackEntryAsState().value?.destination?.route
                if (backstackRoute in listOf(
                        ScreenHome.javaClass.name.toString(),
                        ScreenTask.javaClass.name.toString(),
                        ScreenGallery.javaClass.name.toString(),
                        ScreenAccount.javaClass.name.toString()
                    )
                ) {
                    BottomNavigationBar(navController = navController, selectedIndex) {
                        viewModel.onMenuSelected(it)
                    }
                }
            }
        ) { paddingValue ->
            NavigationHost(navController, paddingValue)
        }

    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController, selectedIndex: Int, onMenuSelected: (Int) -> Unit) {
    val bottomNavItems = listOf(
        BottomNavItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unSelectedIcon = Icons.Outlined.Home,
        ),
        BottomNavItem(
            title = "Task",
            selectedIcon = Icons.Filled.DateRange,
            unSelectedIcon = Icons.Outlined.DateRange
        ),
        BottomNavItem(
            title = "Gallery",
            selectedIcon = Icons.Filled.PlayArrow,
            unSelectedIcon = Icons.Outlined.PlayArrow
        ),
        BottomNavItem(
            title = "Account",
            selectedIcon = Icons.Filled.Person,
            unSelectedIcon = Icons.Outlined.Person
        ),
    )

    NavigationBar(
    ) {

        bottomNavItems.forEachIndexed { index, screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (selectedIndex == index) screen.selectedIcon else screen.unSelectedIcon,
                        contentDescription = screen.title
                    )
                },
                label = { Text(screen.title) },
                selected = selectedIndex == index,
                onClick = {
                    if (selectedIndex != index) {
                        when (screen.title) {
                            "Home" -> navController.navigate(ScreenHome)
                            "Task" -> navController.navigate(ScreenTask)
                            "Gallery" -> navController.navigate(ScreenGallery)
                            "Account" -> navController.navigate(ScreenAccount)
                        }
                    }
                    onMenuSelected(index)
                }
            )
        }
    }
}

@Composable
fun NavigationHost(navController: NavHostController, paddingValue: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = ScreenStart
    ) {
        composable<ScreenStart> {
            StartScreen(navController = navController)
        }

        composable<ScreenLogin> {
            LoginScreen(onNavigateToRegister = {
                navController.navigate(ScreenRegister)
            }, onNavigateToHome = {
                navController.navigate(ScreenHome)
            })
        }
        composable<ScreenRegister> {
            RegisterScreen(onBackPressed = {
                navController.popBackStack()
            }, onNavigateToHome = {
                navController.navigate(ScreenHome)
            })
        }
        composable<ScreenHome> {
            HomeScreen(modifier = Modifier.padding(paddingValue))
        }
        composable<ScreenTask> {
            TaskScreen(onNavigateToCreateTask = {
                navController.navigate(ScreenCreateTask)
            })
        }
        composable<ScreenCreateTask> {
            CreateTaskScreen(modifier = Modifier.padding(paddingValue), onBackPressed = {
                navController.popBackStack()
            })
        }
        composable<ScreenGallery> {
            GalleryScreen()
        }
        composable<ScreenAccount> {
            GalleryScreen()
        }
    }
}


data class BottomNavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
)

@Serializable
object ScreenStart

@Serializable
object ScreenLogin

@Serializable
object ScreenRegister

@Serializable
object ScreenHome

@Serializable
object ScreenTask

@Serializable
object ScreenCreateTask

@Serializable
object ScreenGallery

@Serializable
object ScreenAccount


@Preview
@Composable
private fun MainScreenPrev() {
    AppTheme {
        MainScreen()
    }
}