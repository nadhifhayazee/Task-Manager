package com.nadhifhayazee.taskmanager

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.nadhifhayazee.domain.dto.RequestRegister
import com.nadhifhayazee.domain.repository.UserRepository
import com.nadhifhayazee.domain.usecase.LoginUseCase
import com.nadhifhayazee.domain.usecase.RegisterUseCase
import com.nadhifhayazee.taskmanager.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var loginUseCase: LoginUseCase

    @Inject
    lateinit var registerUseCase: RegisterUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                registerUseCase("fulan@mail.com", "fulan", "fulan").collectLatest {
                    Log.i("fire", it.toString())
                }
//
//                loginUseCase(
//                    "nadhif@mail.com",
//                    "12345"
//                ).collectLatest { it ->
//                    Log.i("fire", it.toString())
//                }
            }


        }

        enableEdgeToEdge()
        setContent {
            AppTheme  {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppTheme {
        Greeting("Android")
    }
}