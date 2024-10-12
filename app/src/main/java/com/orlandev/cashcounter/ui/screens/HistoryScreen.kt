package com.orlandev.cashcounter.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.orlandev.cashcounter.data.database.entity.History
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(modifier: Modifier = Modifier, homeViewModel: HomeViewModel = koinInject()) {

    val allHistory =
        homeViewModel.allHistory.collectAsStateWithLifecycle(initialValue = emptyList())

    Scaffold(modifier = modifier, topBar = {
        LargeTopAppBar(title = {
            Text(text = "Historial")
        }, actions = {
            IconButton(onClick = {

                homeViewModel.deleteAll()

            }) {
                Icon(Icons.Default.Delete, contentDescription = null)
            }
        })

    }) { padding ->
        if (allHistory.value.isNotEmpty()) {
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(allHistory.value, key = { it.id }) {
                    History(it)
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(text = "Sin datos.", style = TextStyle(textAlign = TextAlign.Center))
            }
        }
    }
}

@Composable
fun History(history: History) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = history.data)
    }
}

