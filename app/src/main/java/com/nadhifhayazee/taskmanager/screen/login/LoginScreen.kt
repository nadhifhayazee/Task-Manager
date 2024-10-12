package com.nadhifhayazee.taskmanager.screen.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nadhifhayazee.domain.dto.ResultState
import com.nadhifhayazee.taskmanager.component.CustomButton
import com.nadhifhayazee.taskmanager.component.CustomTextField
import com.nadhifhayazee.taskmanager.ui.theme.AppTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateToRegister: () -> Unit,
    onNavigateToHome: () -> Unit,
) {

    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.loginState.collectLatest {
            when(it) {
                is ResultState.Success -> {
                    onNavigateToHome()
                }
                is ResultState.Error -> {
                    Toast.makeText(context, it.throwable.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CustomTextField(
            label = "Email",
            onTextChange = { newText ->
                email = newText
            },
            placeholder = "Masukkan email",
            keyboardType = KeyboardType.Email
        )

        CustomTextField(
            label = "Password",
            onTextChange = { newText ->
                password = newText
            },
            placeholder = "Masukkan password",
            keyboardType = KeyboardType.Password
        )

        Spacer(Modifier.height(20.dp))
        CustomButton(
            label = "Login",
            onClick = {
                viewModel.login(email, password)
            }
        )

        Text("Atau", modifier.padding(10.dp))


        TextButton(
            onClick = { onNavigateToRegister() },
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "Register Sekarang",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }


    }


}

@Preview
@Composable
private fun LoginScreenPrev() {
    AppTheme {
        LoginScreen(modifier = Modifier.padding(16.dp), onNavigateToRegister = {}, onNavigateToHome = {})
    }

}

