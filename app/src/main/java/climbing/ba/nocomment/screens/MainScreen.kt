//noinspection UsingMaterialAndMaterial3Libraries
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import climbing.ba.nocomment.R
import climbing.ba.nocomment.database.fetchData
import climbing.ba.nocomment.reusables.BottomNavigationBar
import climbing.ba.nocomment.reusables.SearchBar
import climbing.ba.nocomment.reusables.ShowLazyList
import climbing.ba.nocomment.sealed.DataState
import java.time.Year

@SuppressLint("MutableCollectionMutableState")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(navController: NavController) {

    val dataState = remember { mutableStateOf<DataState>(DataState.Loading) }
    val searchQuery = remember { mutableStateOf("") }

    // Selected year state
    val selectedYear = remember { mutableStateOf(Year.now().value) }
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        dataState.value = fetchData()
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Box (
                modifier = Modifier.align(Alignment.TopEnd)
                    .padding(top = 5.dp, end = 10.dp)
            ) {
                TextButton(onClick = { expanded = true }) {
                    Text(
                        text = selectedYear.value.toString(),
                        color = Color.DarkGray,
                        fontSize = 25.sp
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    val currentYear = Year.now().value
                    (currentYear - 2..currentYear).forEach { year ->
                        DropdownMenuItem(onClick = {
                            selectedYear.value = year
                            expanded = false
                        }) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = year.toString(),
                                    color = Color.DarkGray,
                                    fontSize = 20.sp
                                )
                            }
                        }
                    }
                }
            }
            Column(
                modifier = Modifier.padding(horizontal = 2.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.no_comment_logo),
                        contentDescription = "Header Photo",
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // Search bar
                SearchBar(searchQuery.value) { newQuery ->
                    searchQuery.value = newQuery
                }

                Divider(thickness = 1.dp)

                when (val state = dataState.value) {
                    is DataState.Success -> {
                        var memberList by remember { mutableStateOf(state.data) }

                        Column {
                            Spacer(modifier = Modifier.height(8.dp))

                            ShowLazyList(
                                memberList
                                    .filter { member ->
                                        member.fullName.contains(searchQuery.value, ignoreCase = true)
                                    }
                                    .toMutableList(),
                                navController,
                                year = selectedYear.value
                            )
                        }
                    }
                    is DataState.Failure -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = state.message,
                                fontSize = MaterialTheme.typography.h5.fontSize,
                            )
                        }
                    }
                    DataState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    DataState.Empty -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Nema dodanih članova kluba",
                                fontSize = MaterialTheme.typography.h5.fontSize,
                            )
                        }
                    }
                    is DataState.SuccessMember -> TODO()
                    is DataState.SuccessUsers -> TODO()
                }
            }
        }
    }
}
