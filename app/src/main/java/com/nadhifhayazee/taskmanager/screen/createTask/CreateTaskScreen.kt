package com.nadhifhayazee.taskmanager.screen.createTask

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nadhifhayazee.domain.dto.ResultState
import com.nadhifhayazee.taskmanager.component.CustomButton
import com.nadhifhayazee.taskmanager.component.CustomTextField
import com.nadhifhayazee.taskmanager.component.DateTimePickerButton
import com.nadhifhayazee.taskmanager.component.UploadDocumentButton
import kotlinx.coroutines.flow.collectLatest
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateTaskViewModel = hiltViewModel(),
    onBackPressed: () -> Unit
) {
    val context = LocalContext.current
    var subject by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var date by remember { mutableStateOf<Date?>(null) }
    var reminder by remember { mutableStateOf(false) }

    var selectedDocument by remember { mutableStateOf<Uri?>(null) }

    LaunchedEffect(Unit) {
        viewModel.addState.collectLatest {
            when (it) {
                is ResultState.Success -> {
                    if (it.data) {
                        onBackPressed()
                    }
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
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {

        TopAppBar(
            title = {
                Text(
                    "Tambah Task",
                    modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            navigationIcon = {
                IconButton(onClick = {
                    onBackPressed()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        modifier = Modifier.size(20.dp),
                        contentDescription = "Back Button"
                    )
                }
            },
        )

        Column(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            CustomTextField(
                label = "Subject",
                onTextChange = { newText ->
                    subject = newText
                },
                placeholder = "Masukkan subject",
            )

            CustomTextField(
                label = "Deskripsi",
                onTextChange = { newText ->
                    description = newText
                },
                placeholder = "Masukkan deskripsi",
            )


            DateTimePickerButton() { selectedDateTime ->
                date = selectedDateTime
            }

            UploadDocumentButton { uri ->
                selectedDocument = uri
            }

            CustomButton(
                label = "Simpan",
                onClick = {
                    if (subject.isNotEmpty() && description.isNotEmpty() && date != null) {
                        viewModel.addTask(subject, description, date!!, selectedDocument)
                    }
                }
            )
        }


    }
}

