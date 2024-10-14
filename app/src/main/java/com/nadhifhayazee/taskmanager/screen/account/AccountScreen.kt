package com.nadhifhayazee.taskmanager.screen.account

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nadhifhayazee.taskmanager.MainActivity
import com.nadhifhayazee.taskmanager.component.CustomButton
import com.nadhifhayazee.taskmanager.component.CustomTextField
import com.nadhifhayazee.taskmanager.ui.theme.AppTheme

@Composable
fun AccountScreen(modifier: Modifier = Modifier, viewModel: AccountViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val user by viewModel.user.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Text("Account", modifier = modifier.fillMaxWidth(),
            textAlign = TextAlign.Center, style = MaterialTheme.typography.titleLarge)

        user?.let {

            CustomTextField(
                label = "Email",
                onTextChange = { newText ->

                },
                placeholder = "Masukkan email",
                keyboardType = KeyboardType.Email,
                enabled = false,
                defaultValue = user?.email ?: ""
            )

            CustomTextField(
                label = "Username",
                onTextChange = { newText ->

                },
                placeholder = "Masukkan username",
                keyboardType = KeyboardType.Text,
                enabled = false,
                defaultValue = user?.username ?: ""
            )

        }


        CustomButton(
            label = "Logout",
            backgroundColor = MaterialTheme.colorScheme.error,
            modifier = modifier.fillMaxWidth(),
            onClick = {
                viewModel.logout()
                val intent = Intent(context, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                context.startActivity(intent)
            }
        )

    }
}

@Preview
@Composable
private fun AccountScreenPrev() {
    AppTheme {
        AccountScreen()
    }

}