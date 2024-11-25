package com.orlandev.cashcounter.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.orlandev.cashcounter.data.database.entity.History
import com.orlandev.cashcounter.utils.nonScaledSp

@Composable
fun AddHistoryScreen(
    modifier: Modifier = Modifier, navController: NavController, viewModel: HomeViewModel
) {

    val history = viewModel.historyToSave.collectAsStateWithLifecycle(null)

    val context = LocalContext.current

    AddHistoryContent(modifier = modifier, data = history.value, onSaveAction = { historyModified ->
        viewModel.insertHistory(historyModified)
        Toast.makeText(context, "Datos guardados", Toast.LENGTH_LONG).show()
        navController.popBackStack()
    }) {
        navController.popBackStack()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddHistoryContent(
    modifier: Modifier = Modifier,
    data: History?,
    onSaveAction: (History) -> Unit,
    onBack: () -> Unit
) {

    val title = remember {
        mutableStateOf("")
    }

    val errorTitle = remember {
        mutableStateOf("")
    }


    Scaffold(modifier = modifier, topBar = {
        TopAppBar(navigationIcon = {
            IconButton(onClick = {
                onBack()
            }) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }
        }, title = {
            Text("Guardar historial")
        })
    }) { padding ->

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .padding(8.dp)
        ) {

            Column(modifier = Modifier.padding(8.dp)) {

                TextField(
                    label = {
                        Text("Título")
                    },
                    value = title.value,
                    onValueChange = {
                        title.value = it
                    },
                    isError = errorTitle.value.isNotEmpty(),
                )
                if (errorTitle.value.isNotEmpty()) {
                    Text(
                        text = errorTitle.value,
                        style = TextStyle(fontSize = 11.nonScaledSp, color = Color.Red)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = data?.data ?: "")
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Button(onClick = {
                        if (title.value.isNotEmpty()) {
                            if (data == null) errorTitle.value = "Ha currido un error."
                            data?.copy(title = title.value)?.let {
                                onSaveAction(it)
                            }
                        } else {
                            errorTitle.value = "Debe agregar un título."
                        }
                    }) {
                        Text(text = "Guardar")
                    }
                }
            }
        }
    }
}