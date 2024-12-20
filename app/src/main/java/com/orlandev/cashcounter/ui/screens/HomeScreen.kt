package com.orlandev.cashcounter.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.orlandev.cashcounter.R
import com.orlandev.cashcounter.data.Cash
import com.orlandev.cashcounter.data.cashTypesInList
import com.orlandev.cashcounter.data.database.entity.History
import com.orlandev.cashcounter.utils.Date
import com.orlandev.cashcounter.utils.ICashPrint
import com.orlandev.cashcounter.utils.MAX_DIGITS_NUMBER
import com.orlandev.cashcounter.utils.ShareIntent
import com.orlandev.cashcounter.utils.nonScaledSp
import org.koin.compose.koinInject


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    cashPrint: ICashPrint = koinInject(),
    onNavToAddHistory: () -> Unit,
    onNavToHistory: () -> Unit
) {

    val finalCount = remember {
        mutableStateOf("$0")
    }

    val listOfValues = cashTypesInList()

    val textFieldInitValues = listOfValues.map {
        Cash(it, 0)
    }

    val valueStateList =
        remember { mutableStateListOf<Cash>().apply { addAll(textFieldInitValues) } }

    val context = LocalContext.current

    var showOtherCant by remember {
        mutableStateOf(false)
    }


    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        LargeTopAppBar(colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
        ), title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = finalCount.value,
                style = TextStyle(fontSize = 36.nonScaledSp, textAlign = TextAlign.Center)
            )
        }, actions = {

            IconButton(onClick = {

                onNavToHistory()

            }) {
                Icon(Icons.Default.History, contentDescription = null)
            }

            IconButton(onClick = {
                val historyToSave = cashPrint.print(
                    listOfCash = valueStateList.toList(), date = Date.getDate(context = context)
                )

                homeViewModel.historyToSave.value =
                    History(data = historyToSave, title = "", date = Date.now())

                onNavToAddHistory()

                //Toast.makeText(context, "Datos guardados", Toast.LENGTH_LONG).show()

            }) {
                Icon(Icons.Default.Save, contentDescription = null)
            }

            IconButton(enabled = finalCount.value != "$ 0", onClick = {

                val strToShare = cashPrint.print(
                    listOfCash = valueStateList.toList(), date = Date.getDate(context = context)
                )

                ShareIntent.shareIt(context, strToShare, "CashCounter")

            }) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share",
                )
            }

            /*    IconButton(onClick = { *//*TODO*//* }) {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = "History",
                        )
                    }*//*  IconButton(onClick = {


                      }) {
                          Icon(
                              imageVector = Icons.Default.Send,
                              contentDescription = "Send",
                          )
                      }*/
        }, navigationIcon = {
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
    }, floatingActionButton = {
        /* ExtendedFloatingActionButton(onClick = {

             for (i in valueStateList.indices) {
                 valueStateList[i] = valueStateList[i].copy(cant = 0)
             }
             finalCount.value = "$0"

         }) {
             Icon(imageVector = Icons.Default.Save, contentDescription = "Save")
             Spacer(modifier = Modifier.size(10.dp))
             Text(text = stringResource(id = R.string.save_text))
         }*/
    }, bottomBar = {
        Column(modifier = Modifier) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.author),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                style = MaterialTheme.typography.labelSmall
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.phone),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            listOfValues.forEachIndexed { index, cashType ->
                if (!showOtherCant && index >= listOfValues.size - 3) {
                    MoreSelector(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                    ) {
                        showOtherCant = !showOtherCant
                    }
                    return@Column
                }
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

                                finalCount.value = "$$result"
                            },
                            textStyle = TextStyle(
                                fontSize = 16.nonScaledSp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MoreSelector(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Surface(modifier = modifier, onClick = onClick, color = Color.Transparent) {
        Box(modifier = Modifier.fillMaxSize()) {
            HorizontalDivider(modifier = Modifier.align(Alignment.Center))
            Icon(
                Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}