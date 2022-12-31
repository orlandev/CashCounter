package com.orlandev.cashcounter.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orlandev.cashcounter.R
import com.orlandev.cashcounter.data.Cash
import com.orlandev.cashcounter.data.cashTypesInList
import com.orlandev.cashcounter.ui.theme.CashCounterTheme
import com.orlandev.cashcounter.utils.MAX_DIGITS_NUMBER


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

    val finalCount = remember {
        mutableStateOf("$0")
    }

    val listOfValues = cashTypesInList()

    val textFieldInitValues = listOfValues.map {
        Cash(it, 0)
    }

    val valueStateList =
        remember { mutableStateListOf<Cash>().apply { addAll(textFieldInitValues) } }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            LargeTopAppBar(
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                title = {
                    Text(text = finalCount.value)
                },
                actions = {

                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share",
                        )
                    }

                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = "History",
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Send",
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        for (i in valueStateList.indices) {
                            valueStateList[i] = valueStateList[i].copy(cant = 0)
                        }
                        finalCount.value = "$0"
                    }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "New")
                    }
                }

            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save")
                Spacer(modifier = Modifier.size(10.dp))
                Text(text = stringResource(id = R.string.save_text))
            }
        }

    ) { paddingValues ->


        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {


            listOfValues.forEachIndexed { index, cashType ->
                item {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = cashType.value.toString(),
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.weight(2f),
                            textAlign = TextAlign.Center
                        )
                        Box(
                            modifier = Modifier
                                .height(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(MaterialTheme.colorScheme.primaryContainer)
                                .weight(2f),
                            contentAlignment = Alignment.Center
                        ) {
                            BasicTextField(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp),
                                value = valueStateList[index].cant.toString(),
                                singleLine = true,
                                maxLines = 1,
                                keyboardActions = KeyboardActions(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                onValueChange = {
                                    if (it.isNotEmpty()) {
                                        if (it.length <= MAX_DIGITS_NUMBER) {
                                            valueStateList[index] =
                                                valueStateList[index].copy(cant = it.toLong())
                                        }
                                    } else {
                                        valueStateList[index] = valueStateList[index].copy(cant = 0)
                                    }
                                    val result = valueStateList.sumOf { currentCash ->
                                        currentCash.calculate()
                                    }

                                    finalCount.value = "$ $result"
                                },
                                textStyle = MaterialTheme.typography.titleMedium,

                                )
                        }
                    }
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    CashCounterTheme {
        HomeScreen()
    }
}