package com.orlandev.cashcounter.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.orlandev.cashcounter.data.database.entity.History
import com.orlandev.cashcounter.utils.ShareIntent
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier, homeViewModel: HomeViewModel = koinInject(), onBack: () -> Unit
) {

    val allHistory =
        homeViewModel.allHistory.collectAsStateWithLifecycle(initialValue = emptyList())

    Scaffold(modifier = modifier, topBar = {
        LargeTopAppBar(title = {
            Text(text = "Historial")
        }, navigationIcon = {
            IconButton(onClick = {
                onBack()
            }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
            }
        }, actions = {
            IconButton(onClick = {

                homeViewModel.deleteAllHistory()

            }) {
                Icon(Icons.Default.Delete, contentDescription = null)
            }
        })

    }) { padding ->
        if (allHistory.value.isNotEmpty()) {
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(allHistory.value, key = { it.id }) {
                    HistoryCard(it) {
                        homeViewModel.deleteHistoryById(it.id)
                    }
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
fun HistoryCard(history: History, onDelete: () -> Unit) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalAlignment = Alignment.Bottom,

    ) {
        Card(
            modifier = Modifier
                .weight(0.8f)
                .padding(8.dp)
        ) {
            Text(modifier = Modifier.padding(8.dp), text = history.data)

        }

        Column(
            modifier = Modifier.fillMaxHeight().padding(4.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(onClick = {
                ShareIntent.shareIt(context, history.data, "CashCounter")
            }) {
                Icon(Icons.Default.Share, contentDescription = null)
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(colors = ButtonColors(
                containerColor = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.onErrorContainer,
                disabledContentColor = Color.Gray,
                disabledContainerColor = Color.Gray.copy(alpha = 0.7f)
            ), onClick = {

                onDelete()

            }) {
                Icon(Icons.Default.Delete, contentDescription = null)
            }

        }
    }

}

